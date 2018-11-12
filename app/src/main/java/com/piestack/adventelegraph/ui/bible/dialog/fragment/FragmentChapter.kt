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
import com.piestack.adventelegraph.models.event.ChapterEvent
import com.piestack.adventelegraph.models.room.Book
import com.piestack.adventelegraph.ui.bible.BibleActivityViewModel
import com.piestack.adventelegraph.util.RxBus
import com.piestack.adventelegraph.util.getViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_book_sample.view.*
import timber.log.Timber
import javax.inject.Inject

class FragmentChapter : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: BibleActivityViewModel

    private lateinit var books: ArrayList<Book>
    private var book: Int = 0
    private lateinit var mAdapter: FragmentChapterAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return activity?.layoutInflater!!.inflate(R.layout.fragment_book_sample, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(this, viewModelFactory)
        viewModel.getBookClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = FragmentChapterAdapter(requireActivity(), books[book].NumOfChapters)
        view.gridview.adapter = mAdapter
        view.gridview.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    RxBus.getInstance().send(ChapterEvent(position, book))
                }

        viewModel.clickedBook.observe(this, Observer {
            it?.let { bookEvent ->
                book = bookEvent.position
                mAdapter.apply {
                    refillAdapter(bookEvent.chapters)
                }
                Timber.e("Updated dialog fragment chapters to: ${bookEvent.chapters}")
            }
        })

    }

    companion object {
        fun createInstance(books: ArrayList<Book>): FragmentChapter {
            val fragment = FragmentChapter()
            fragment.books = books
            return fragment
        }
    }

}