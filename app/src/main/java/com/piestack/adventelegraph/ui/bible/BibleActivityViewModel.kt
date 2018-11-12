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

package com.piestack.adventelegraph.ui.bible

import androidx.lifecycle.MutableLiveData
import com.piestack.adventelegraph.models.event.BookEvent
import com.piestack.adventelegraph.models.event.ChapterEvent
import com.piestack.adventelegraph.models.event.NewPageEvent
import com.piestack.adventelegraph.models.event.VerseEvent
import com.piestack.adventelegraph.models.room.Book
import com.piestack.adventelegraph.models.room.kjv
import com.piestack.adventelegraph.repository.LocalRepository
import com.piestack.adventelegraph.ui.base.RxViewModel
import com.piestack.adventelegraph.util.RxBus
import com.piestack.adventelegraph.util.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

class BibleActivityViewModel @Inject constructor(private val localRepository: LocalRepository, private val schedulerProvider: SchedulerProvider) : RxViewModel() {

    var allVersesResult = MutableLiveData<ArrayList<kjv>>()
    var getVerseResult = MutableLiveData<kjv>()
    var getChapterResult = MutableLiveData<ArrayList<kjv>>()
    var getRangeResult = MutableLiveData<ArrayList<kjv>>()
    var getNoOfChaptersResult = MutableLiveData<Int>()
    var getNoOfVersesResult = MutableLiveData<Int>()
    var getBooksResult = MutableLiveData<ArrayList<Book>>()

    var newPage = MutableLiveData<NewPageEvent>()
    var clickedBook = MutableLiveData<BookEvent>()
    var clickedChapter = MutableLiveData<ChapterEvent>()
    var clickedVerse = MutableLiveData<VerseEvent>()

    override fun subscribe() {

    }

    fun allVerses() {
        val disposable = localRepository.allVerses
                .compose(schedulerProvider.getSchedulersForFlowable()).subscribe({
                    allVersesResult.value = it
                }, {
                    Timber.e(it, it.message)
                })
        disposables.add(disposable)
    }

    fun getVerse(verse: Int) {
        val disposable = localRepository.getVerse(verse)
                .compose(schedulerProvider.getSchedulersForFlowable()).subscribe({
                    getVerseResult.value = it
                }, {
                    Timber.e(it, it.message)
                })
        disposables.add(disposable)
    }

    fun getChapter(chapter: Int, book: Int) {
        val disposable = localRepository.getChapter(chapter, book)
                .compose(schedulerProvider.getSchedulersForFlowable()).subscribe({
                    getChapterResult.value = it
                }, {
                    Timber.e(it, it.message)
                })
        disposables.add(disposable)
    }

    fun getRange(start: Int, end: Int) {
        val disposable = localRepository.getRange(start, end)
                .compose(schedulerProvider.getSchedulersForFlowable()).subscribe({
                    getRangeResult.value = it
                }, {
                    Timber.e(it, it.message)
                })
        disposables.add(disposable)
    }

    fun getNoOfChapters(book: Int) {
        val disposable = localRepository.getNoOfChapters(book)
                .compose(schedulerProvider.getSchedulersForFlowable()).subscribe({
                    getNoOfChaptersResult.value = it
                }, {
                    Timber.e(it, it.message)
                })
        disposables.add(disposable)
    }

    fun getNoOfVerses(chapter: Int, book: Int) {
        val disposable = localRepository.getNoOfVerses(chapter, book)
                .compose(schedulerProvider.getSchedulersForFlowable()).subscribe({
                    getNoOfVersesResult.value = it
                }, {
                    Timber.e(it, it.message)
                })
        disposables.add(disposable)
    }

    fun insertBible(bible: ArrayList<kjv>) {
        localRepository.insertBible(bible)
    }

    fun deleteBible() {
        localRepository.deleteBible()
    }

    fun getBooks() {
        val disposable = localRepository.getBooks()
                .compose(schedulerProvider.getSchedulersForFlowable()).subscribe({
                    getBooksResult.value = it
                }, {
                    Timber.e(it, it.message)
                })
    }


    fun getNewPage() {
        val disposable = RxBus.getInstance().toObservable(NewPageEvent::class.java)
                .compose(schedulerProvider.getSchedulersForObservable()).subscribe({
                    newPage.value = it
                }, {
                    Timber.e(it, it.message)
                })
        disposables.add(disposable)
    }

    fun getBookClicked() {
        val disposable = RxBus.getInstance().toObservable(BookEvent::class.java)
                .compose(schedulerProvider.getSchedulersForObservable()).subscribe({
                    clickedBook.value = it
                }, {
                    Timber.e(it, it.message)
                })
        disposables.add(disposable)
    }

    fun getChapterClicked() {
        val disposable = RxBus.getInstance().toObservable(ChapterEvent::class.java)
                .compose(schedulerProvider.getSchedulersForObservable()).subscribe({
                    clickedChapter.value = it
                }, {
                    Timber.e(it, it.message)
                })
        disposables.add(disposable)
    }

    fun getVerseClicked() {
        val disposable = RxBus.getInstance().toObservable(VerseEvent::class.java)
                .compose(schedulerProvider.getSchedulersForObservable()).subscribe({
                    clickedVerse.value = it
                }, {
                    Timber.e(it, it.message)
                })
        disposables.add(disposable)
    }

}