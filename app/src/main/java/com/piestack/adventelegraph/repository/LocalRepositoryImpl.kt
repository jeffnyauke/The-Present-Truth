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

import com.piestack.adventelegraph.local.BooksDAO
import com.piestack.adventelegraph.local.KjvDAO
import com.piestack.adventelegraph.models.room.Book
import com.piestack.adventelegraph.models.room.Kjv
import io.reactivex.Flowable
import java.util.concurrent.Executor

class LocalRepositoryImpl(private val kjvDAO: KjvDAO, private val booksDAO: BooksDAO, private val executor: Executor) : LocalRepository {

    override val allVerses: Flowable<ArrayList<Kjv>>
        get() = kjvDAO.getAll() as Flowable<ArrayList<Kjv>>

    override fun getVerse(id: Int): Flowable<Kjv> {
        return kjvDAO.getVerse(id)
    }

    override fun getChapter(chapter: Int, book: Int): Flowable<ArrayList<Kjv>> {
        return kjvDAO.getChapter(chapter, book) as Flowable<ArrayList<Kjv>>
    }

    override fun getRange(start: Int, end: Int): Flowable<ArrayList<Kjv>> {
        return kjvDAO.getRange(start, end) as Flowable<ArrayList<Kjv>>
    }

    override fun getNoOfChapters(book: Int): Flowable<Int> {
        return kjvDAO.getNoOfChapters(book)
    }

    override fun getNoOfVerses(chapter: Int, book: Int): Flowable<Int> {
        return kjvDAO.getNoOfVerses(chapter, book)
    }

    override fun insertBible(bible: ArrayList<Kjv>) {
        executor.execute { kjvDAO.insert(bible) }
    }

    override fun deleteBible() {
        executor.execute { kjvDAO.deleteAll() }
    }

    override fun getBooks(): Flowable<ArrayList<Book>> {
        return booksDAO.getBooks() as Flowable<ArrayList<Book>>
    }

}
