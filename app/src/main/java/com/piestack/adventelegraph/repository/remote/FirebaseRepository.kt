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
import com.google.firebase.firestore.QuerySnapshot
import com.piestack.adventelegraph.models.Filters
import io.reactivex.Observable

interface FirebaseRepository {

    fun listPosts(filters: Filters): Observable<QuerySnapshot>

    fun listPosts(filters: Filters, documentSnapshot: DocumentSnapshot): Observable<QuerySnapshot>
}