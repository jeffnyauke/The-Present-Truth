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
import com.google.firebase.firestore.Query
import com.piestack.adventelegraph.models.filters.Articles
import timber.log.Timber


class FirebaseRepositoryImpl : FirebaseRepository {

    override fun applyFilters(query: Query, articleFilter: Articles, documentSnapshot: DocumentSnapshot?): Query {
        var filteredQuery = query

        if (articleFilter.author.isNotBlank()) {
            filteredQuery = filteredQuery.whereEqualTo("uid", articleFilter.author)
        }

        if (articleFilter.category.isNotBlank()) {
            filteredQuery = filteredQuery.whereEqualTo("category", articleFilter.category)
        }

        filteredQuery = if (articleFilter.primary) {
            filteredQuery.whereEqualTo("primary", true)
        } else {
            filteredQuery.whereEqualTo("primary", false)
        }

        filteredQuery = if (articleFilter.published) {
            filteredQuery.whereEqualTo("published", true)
        } else {
            filteredQuery.whereEqualTo("published", false)
        }

        filteredQuery = when {
            articleFilter.sort == "published_date" -> filteredQuery.orderBy("published_date", Query.Direction.DESCENDING)
            articleFilter.sort == "date_created" -> filteredQuery.orderBy("date_created", Query.Direction.DESCENDING)
            articleFilter.sort == "views" -> filteredQuery.orderBy("metrics.views", Query.Direction.DESCENDING)
            articleFilter.sort == "likes" -> filteredQuery.orderBy("metrics.likes", Query.Direction.DESCENDING)
            articleFilter.sort == "shares" -> filteredQuery.orderBy("metrics.shares", Query.Direction.DESCENDING)
            else -> {
                filteredQuery.orderBy("published_date", Query.Direction.DESCENDING)
            }
        }

        if (documentSnapshot != null) {
            filteredQuery.startAfter(documentSnapshot)
        }

        filteredQuery = filteredQuery.limit(articleFilter.limit)

        Timber.e(articleFilter.toString())

        return filteredQuery
    }
}