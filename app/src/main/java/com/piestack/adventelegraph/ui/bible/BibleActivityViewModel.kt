package com.piestack.adventelegraph.ui.bible

import androidx.lifecycle.ViewModel
import com.piestack.adventelegraph.models.room.CountResult
import com.piestack.adventelegraph.models.room.kjv
import com.piestack.adventelegraph.repository.LocalRepository
import com.piestack.adventelegraph.util.SchedulerProvider
import io.reactivex.Flowable

class BibleActivityViewModel constructor(private val localRepository: LocalRepository, private val schedulerProvider: SchedulerProvider) : ViewModel() {

    fun allVerses(): Flowable<ArrayList<kjv>> = localRepository.allVerses
            .compose(schedulerProvider.getSchedulersForFlowable())

    fun getVerse(verse: Int): Flowable<kjv> = localRepository.getVerse(verse)
            .compose(schedulerProvider.getSchedulersForFlowable())

    fun getChapter(chapter: Int, book: Int): Flowable<ArrayList<kjv>> = localRepository.getChapter(chapter, book)
            .compose(schedulerProvider.getSchedulersForFlowable())

    fun getRange(start: Int, end: Int): Flowable<ArrayList<kjv>> = localRepository.getRange(start, end)
            .compose(schedulerProvider.getSchedulersForFlowable())

    fun getNoOfChapters(book: Int): Flowable<CountResult> = localRepository.getNoOfChapters(book)
            .compose(schedulerProvider.getSchedulersForFlowable())

    fun getNoOfVerses(chapter: Int, book: Int): Flowable<CountResult> = localRepository.getNoOfVerses(chapter, book)
            .compose(schedulerProvider.getSchedulersForFlowable())

    fun insertBible(bible: ArrayList<kjv>) {
        localRepository.insertBible(bible)
    }

    fun deleteBible() {
        localRepository.deleteBible()
    }

    fun getBooks() = localRepository.getBooks().compose(schedulerProvider.getSchedulersForFlowable())

}