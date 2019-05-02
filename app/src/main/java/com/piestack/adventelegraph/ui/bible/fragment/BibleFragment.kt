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

package com.piestack.adventelegraph.ui.bible.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.di.ViewModelFactory
import com.piestack.adventelegraph.models.event.NewPageEvent
import com.piestack.adventelegraph.models.room.Book
import com.piestack.adventelegraph.ui.bible.BibleActivityViewModel
import com.piestack.adventelegraph.ui.bible.BibleAdapter
import com.piestack.adventelegraph.util.RxBus
import com.piestack.adventelegraph.util.getViewModel
import com.piestack.adventelegraph.util.vertical
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_bible.view.*
import timber.log.Timber
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class BibleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: BibleActivityViewModel

    private lateinit var bibleAdapter: BibleAdapter

    private var newPageEvent = NewPageEvent()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bible, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(this, viewModelFactory)
        viewModel.getChapter(newPageEvent.chapter, newPageEvent.book)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getChapterResult.observe(this, Observer {
            it?.let { verses ->
                bibleAdapter = BibleAdapter(verses)
                view.recycler.apply {
                    vertical()
                    adapter = bibleAdapter
                    layoutManager?.scrollToPosition(newPageEvent.verse - 1)
                }
                try {
                    Timber.e(verses.size.toString())
                    Timber.e(verses[0].t)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                RxBus.getInstance().send(newPageEvent)
            }
        })
    }

    companion object {
        fun createInstance(position: Int, books: ArrayList<Book>, verse: Int): BibleFragment {
            val fragment = BibleFragment()

            var cumulative = 0
            var bookNo = 0

            for ((i, book) in books.withIndex()) {
                Timber.e("Iterations: $position $i")
                bookNo += 1
                cumulative += book.NumOfChapters

                if (position >= cumulative) continue
                else {
                    fragment.newPageEvent.book = bookNo
                    fragment.newPageEvent.chapter = (position + 1) - (cumulative - book.NumOfChapters)
                    fragment.newPageEvent.verse = verse
                    Timber.e("Else: $position ${fragment.newPageEvent.book} ${fragment.newPageEvent.chapter} ${fragment.newPageEvent.verse}")
                    break
                }
            }

            return fragment
        }
    }

}
