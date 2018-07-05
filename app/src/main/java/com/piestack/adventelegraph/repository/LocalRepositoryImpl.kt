package com.piestack.adventelegraph.repository

import com.piestack.adventelegraph.local.BooksDAO
import com.piestack.adventelegraph.local.KjvDAO
import com.piestack.adventelegraph.models.room.Book
import com.piestack.adventelegraph.models.room.CountResult
import com.piestack.adventelegraph.models.room.kjv
import io.reactivex.Flowable
import java.util.concurrent.Executor

class LocalRepositoryImpl(private val kjvDAO: KjvDAO, private val booksDAO: BooksDAO, private val executor: Executor) : LocalRepository {

    override val allVerses: Flowable<ArrayList<kjv>>
        get() = kjvDAO.getAll() as Flowable<ArrayList<kjv>>

    override fun getVerse(id: Int): Flowable<kjv> {
        return kjvDAO.getVerse(id)
    }

    override fun getChapter(chapter: Int, book: Int): Flowable<ArrayList<kjv>> {
        return kjvDAO.getChapter(chapter, book) as Flowable<ArrayList<kjv>>
    }

    override fun getRange(start: Int, end: Int): Flowable<ArrayList<kjv>> {
        return kjvDAO.getRange(start, end) as Flowable<ArrayList<kjv>>
    }

    override fun getNoOfChapters(book: Int): Flowable<CountResult> {
        return kjvDAO.getNoOfChapters(book)
    }

    override fun getNoOfVerses(chapter: Int, book: Int): Flowable<CountResult> {
        return kjvDAO.getNoOfVerses(chapter, book)
    }

    override fun insertBible(bible: ArrayList<kjv>) {
        executor.execute { kjvDAO.insert(bible) }
    }

    override fun deleteBible() {
        executor.execute { kjvDAO.deleteAll() }
    }

    override fun getBooks(): Flowable<ArrayList<Book>> {
        return booksDAO.getBooks() as Flowable<ArrayList<Book>>
    }

}
