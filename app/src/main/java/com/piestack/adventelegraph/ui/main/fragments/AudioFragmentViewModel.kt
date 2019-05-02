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

package com.piestack.adventelegraph.ui.main.fragments

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.piestack.adventelegraph.app.Config
import com.piestack.adventelegraph.app.media
import com.piestack.adventelegraph.models.Media
import com.piestack.adventelegraph.models.filters.Articles
import com.piestack.adventelegraph.repository.remote.FirebaseRepository
import com.piestack.adventelegraph.ui.base.ScopedViewModel
import com.piestack.adventelegraph.util.firebase.FirebaseQueryLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


class AudioFragmentViewModel @Inject constructor(private val fireStore: FirebaseFirestore, private val firebaseRepository: FirebaseRepository, private val auth: FirebaseAuth) : ScopedViewModel() {

    var lastVisible: DocumentSnapshot? = null
    var articleCount = 0

    private var musicLiveData = MutableLiveData<MutableList<Media>>()

    private lateinit var music : FirebaseQueryLiveData

    private inner class Deserializer : androidx.arch.core.util.Function<QuerySnapshot, MutableList<Media>> {
        /**
         * Applies this function to the given input.
         *
         * @param input the input
         * @return the function result.
         */
        override fun apply(input: QuerySnapshot): MutableList<Media>? {
            when {
                !input.isEmpty -> {
                    lastVisible = input.last()
                    articleCount = input.size()
                }
            }
            return input.toObjects(Media::class.java)
        }
    }

    @NonNull
    fun getMusic(): LiveData<MutableList<Media>> {
        return musicLiveData
    }

    //var user = MutableLiveData<FirebaseUser>()

    init {
        signInAnonymouslyWhenNecessary()
    }

    private fun signInAnonymouslyWhenNecessary() {
        launch {
            withContext(Dispatchers.Main) {
                if (auth.currentUser == null)
                    auth.signInAnonymously()
                            .addOnSuccessListener { authResult ->
                                Timber.d("signInAnonymously:SUCCESS")
                                //user.postValue(authResult.user)
                                music = FirebaseQueryLiveData(firebaseRepository.applyFilters(fireStore.media(), Articles().apply { category = Config.MUSIC }, lastVisible))
                                musicLiveData.postValue(Transformations.map(music, Deserializer()).value)
                            }
                            .addOnFailureListener { exception ->
                                Timber.e(exception, "signInAnonymously:FAILURE")
                            }
                else {
                    music = FirebaseQueryLiveData(firebaseRepository.applyFilters(fireStore.media(), Articles().apply { category = Config.MUSIC }, lastVisible))
                    musicLiveData.postValue(Transformations.map(music, Deserializer()).value)
                }
            }
        }
    }
}