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

package com.piestack.adventelegraph.app

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by Jeffrey Nyauke on 27,December,2018.
 * Piestack company.
 * Nairobi, KE.
 * hello@piestack.co.ke
 */
fun FirebaseFirestore.articles() = collection(Config.FIREBASE_ARTICLES)

fun FirebaseFirestore.articleDoc(uid: String) = articles().document(uid)

fun FirebaseFirestore.media() = collection(Config.FIREBASE_MEDIA)

fun FirebaseFirestore.mediaDoc(uid: String) = media().document(uid)

fun FirebaseFirestore.quiz() = collection(Config.FIREBASE_QUIZ)

fun FirebaseFirestore.quizDoc(uid: String) = quiz().document(uid)


