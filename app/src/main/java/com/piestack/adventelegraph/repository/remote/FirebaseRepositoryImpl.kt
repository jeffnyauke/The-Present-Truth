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

package com.piestack.adventelegraph.repository.remote

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.piestack.adventelegraph.models.Filters
import io.reactivex.Observable
import timber.log.Timber


class FirebaseRepositoryImpl constructor(private val fireStore: FirebaseFirestore) : FirebaseRepository {

    override fun listPosts(filters: Filters): Observable<QuerySnapshot> {

        return Observable.create { emitter ->
            var query: Query = fireStore.collection(ARTICLES)

            if (filters.author.isNotBlank()) {
                query = query.whereEqualTo("uid", filters.author)
            }

            if (filters.category.isNotBlank()) {
                query = query.whereEqualTo("category", filters.category)
            }

            query = if (filters.primary) {
                query.whereEqualTo("primary", true)
            } else {
                query.whereEqualTo("primary", false)
            }

            query = if (filters.published) {
                query.whereEqualTo("published", true)
            } else {
                query.whereEqualTo("published", false)
            }

            query = when {
                filters.sort == "published_date" -> query.orderBy("published_date", Query.Direction.DESCENDING)
                filters.sort == "date_created" -> query.orderBy("date_created", Query.Direction.DESCENDING)
                filters.sort == "views" -> query.orderBy("metrics.views", Query.Direction.DESCENDING)
                filters.sort == "likes" -> query.orderBy("metrics.likes", Query.Direction.DESCENDING)
                filters.sort == "shares" -> query.orderBy("metrics.shares", Query.Direction.DESCENDING)
                else -> {
                    query.orderBy("published_date", Query.Direction.DESCENDING)
                }
            }

            //if (lastVisible != null) query.startAfter(lastVisible!!)

            query = query.limit(filters.limit)

            Timber.e(filters.toString())

            query.addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Timber.w(e, e.message)
                    emitter.onError(e)
                    return@addSnapshotListener
                }
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    emitter.onNext(querySnapshot)
                } else {
                    Timber.e("Current posts: null")
                }
            }
        }
    }

    override fun listPosts(filters: Filters, documentSnapshot: DocumentSnapshot): Observable<QuerySnapshot> {

        return Observable.create { emitter ->
            var query: Query = fireStore.collection(ARTICLES)

            if (filters.author.isNotBlank()) {
                query = query.whereEqualTo("uid", filters.author)
            }

            if (filters.category.isNotBlank()) {
                query = query.whereEqualTo("category", filters.category)
            }

            query = if (filters.primary) {
                query.whereEqualTo("primary", true)
            } else {
                query.whereEqualTo("primary", false)
            }

            query = if (filters.published) {
                query.whereEqualTo("published", true)
            } else {
                query.whereEqualTo("published", false)
            }

            query = when {
                filters.sort == "published_date" -> query.orderBy("published_date", Query.Direction.DESCENDING)
                filters.sort == "date_created" -> query.orderBy("date_created", Query.Direction.DESCENDING)
                filters.sort == "views" -> query.orderBy("metrics.views", Query.Direction.DESCENDING)
                filters.sort == "likes" -> query.orderBy("metrics.likes", Query.Direction.DESCENDING)
                filters.sort == "shares" -> query.orderBy("metrics.shares", Query.Direction.DESCENDING)
                else -> {
                    query.orderBy("published_date", Query.Direction.DESCENDING)
                }
            }

            query.startAfter(documentSnapshot)

            query = query.limit(filters.limit)

            Timber.e(filters.toString())

            query.addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    Timber.w(e, e.message)
                    emitter.onError(e)
                    return@addSnapshotListener
                }
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    emitter.onNext(querySnapshot)
                } else {
                    Timber.e("Current posts: null")
                }
            }
        }
    }

    companion object {
        val ARTICLES = "articles"
    }
}