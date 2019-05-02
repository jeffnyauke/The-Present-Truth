/*
 * Copyright (c) 2019. Jeffrey Nyauke.
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

package com.piestack.adventelegraph.ui.search

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.piestack.adventelegraph.app.articles
import com.piestack.adventelegraph.models.filters.Articles
import com.piestack.adventelegraph.models.Post
import com.piestack.adventelegraph.repository.remote.FirebaseRepository
import com.piestack.adventelegraph.ui.base.ScopedViewModel
import com.piestack.adventelegraph.util.firebase.FirebaseQueryLiveData
import javax.inject.Inject


class SearchActivityViewModel @Inject constructor(fireStore: FirebaseFirestore, firebaseRepository: FirebaseRepository) : ScopedViewModel() {

    var lastVisible: DocumentSnapshot? = null
    var articleCount = 0

    private val articles = FirebaseQueryLiveData(firebaseRepository.applyFilters(fireStore.articles(), Articles().apply { }, lastVisible))

    private val articlesLiveData: LiveData<MutableList<Post>> = Transformations.map(articles, Deserializer())

    private inner class Deserializer : androidx.arch.core.util.Function<QuerySnapshot, MutableList<Post>> {
        /**
         * Applies this function to the given input.
         *
         * @param input the input
         * @return the function result.
         */
        override fun apply(input: QuerySnapshot): MutableList<Post>? {
            when {
                !input.isEmpty -> {
                    lastVisible = input.last()
                    articleCount = input.size()
                }
            }
            return input.toObjects(Post::class.java)
        }
    }

    @NonNull
    fun search(): LiveData<MutableList<Post>> {
        return articlesLiveData
    }
}