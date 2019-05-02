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

package com.piestack.adventelegraph.ui.bible.dialog.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.di.ViewModelFactory
import com.piestack.adventelegraph.models.event.VerseEvent
import com.piestack.adventelegraph.ui.bible.BibleActivityViewModel
import com.piestack.adventelegraph.util.RxBus
import com.piestack.adventelegraph.util.getViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_book_sample.view.*
import timber.log.Timber
import javax.inject.Inject

class FragmentVerse : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: BibleActivityViewModel

    private var numOfVerses = 40
    private var book = 0
    private var chapter = 0
    private lateinit var mAdapter: FragmentVerseAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return activity?.layoutInflater!!.inflate(R.layout.fragment_book_sample, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(this, viewModelFactory)
        viewModel.getChapterClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = FragmentVerseAdapter(requireActivity(), numOfVerses)
        view.gridview.adapter = mAdapter

        view.gridview.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    RxBus.getInstance().send(VerseEvent(position, chapter, book))
                }

        viewModel.clickedChapter.observe(this, Observer { it ->
            it?.let { chapterEvent ->
                book = chapterEvent.book
                chapter = chapterEvent.position
                Timber.e("Updating dialog fragment verses to chapter: $chapter $book")
                viewModel.getNoOfVerses(chapter + 1, book + 1)
                viewModel.getNoOfChaptersResult.observe(this, Observer { it ->
                    it?.let { chapterVerses ->
                        mAdapter.apply {
                            refillAdapter(chapterVerses)
                        }
                        Timber.e("The chapter has this many verses: $chapterVerses")
                    }
                })
            }
        })
    }

    companion object {
        fun createInstance(numofVerses: Int): FragmentVerse {
            val fragment = FragmentVerse()
            fragment.numOfVerses = numofVerses
            return fragment
        }
    }
}