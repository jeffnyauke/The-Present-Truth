package com.piestack.adventelegraph.repository

import com.piestack.adventelegraph.models.room.Book
import com.piestack.adventelegraph.models.room.CountResult
import com.piestack.adventelegraph.models.room.kjv
import io.reactivex.Flowable

interface LocalRepository {

    val allVerses: Flowable<ArrayList<kjv>>

    fun getVerse(id: Int): Flowable<kjv>

    fun getChapter(chapter: Int, book: Int): Flowable<ArrayList<kjv>>

    fun getRange(start: Int, end: Int): Flowable<ArrayList<kjv>>

    fun getNoOfChapters(book: Int): Flowable<CountResult>

    fun getNoOfVerses(chapter: Int, book: Int): Flowable<CountResult>

    fun insertBible(bible: ArrayList<kjv>)

    fun deleteBible()

    fun getBooks(): Flowable<ArrayList<Book>>

}
