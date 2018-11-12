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

package com.piestack.adventelegraph.repository

import com.piestack.adventelegraph.models.room.Book
import com.piestack.adventelegraph.models.room.kjv
import io.reactivex.Flowable

interface LocalRepository {

    val allVerses: Flowable<ArrayList<kjv>>

    fun getVerse(id: Int): Flowable<kjv>

    fun getChapter(chapter: Int, book: Int): Flowable<ArrayList<kjv>>

    fun getRange(start: Int, end: Int): Flowable<ArrayList<kjv>>

    fun getNoOfChapters(book: Int): Flowable<Int>

    fun getNoOfVerses(chapter: Int, book: Int): Flowable<Int>

    fun insertBible(bible: ArrayList<kjv>)

    fun deleteBible()

    fun getBooks(): Flowable<ArrayList<Book>>

}
