/*
 * Copyright (c) 2018. Jeffrey Nyauke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.piestack.adventelegraph.ui.main

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.piestack.adventelegraph.models.Filters
import com.piestack.adventelegraph.models.Post
import com.piestack.adventelegraph.repository.remote.FirebaseRepository
import com.piestack.adventelegraph.ui.base.RxViewModel
import com.piestack.adventelegraph.util.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository, private val schedulerProvider: SchedulerProvider) : RxViewModel() {

    var posts = MutableLiveData<ArrayList<Post>>()
    var lastVisible: DocumentSnapshot? = null
    var size = 0
    val listPost: ArrayList<Post> = ArrayList()

    override fun subscribe() {
    }

    fun filterPosts(filters: Filters) {
        val disposable = if (lastVisible == null) {
            firebaseRepository.listPosts(filters)
                    .compose(schedulerProvider.getSchedulersForObservable()).subscribe({
                        listPost.clear()
                        for (document in it) {
                            Timber.e("Current posts: ${document.data}")
                            val post = document.toObject(Post::class.java)
                            listPost.apply {
                                add(post)
                            }
                            lastVisible = it.documents[it.size() - 1]
                            size = it.size()
                        }
                        posts.value = listPost
                    }, {
                        Timber.e(it, it.message)
                    })
        } else {
            firebaseRepository.listPosts(filters, lastVisible!!)
                    .compose(schedulerProvider.getSchedulersForObservable()).subscribe({
                        for (document in it) {
                            Timber.e("Current posts: ${document.data}")
                            val post = document.toObject(Post::class.java)
                            listPost.apply { add(post) }
                            lastVisible = it.documents[it.size() - 1]
                            size = it.size()
                        }
                        posts.value = listPost
                    }, {
                        Timber.e(it, it.message)
                    })
        }
        disposables.add(disposable)
    }
}