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

package com.piestack.adventelegraph.ui.bible.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.di.ViewModelFactory
import com.piestack.adventelegraph.ui.bible.BibleActivityViewModel
import com.piestack.adventelegraph.ui.bible.dialog.fragment.FragmentBook
import com.piestack.adventelegraph.ui.bible.dialog.fragment.FragmentChapter
import com.piestack.adventelegraph.ui.bible.dialog.fragment.FragmentVerse
import com.piestack.adventelegraph.util.getViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class TabbedDialog : DialogFragment() {

    private lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    private var bookname: String = ""
    private var chapter: Int = 0
    private var verse: Int = 0

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: BibleActivityViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootview = activity?.layoutInflater?.inflate(R.layout.dialog_bible, container, false)

        this.setStyle(androidx.fragment.app.DialogFragment.STYLE_NO_INPUT, R.style.Theme_Advent_Dialog)

        tabLayout = rootview?.findViewById<View>(R.id.tabLayout) as TabLayout
        viewPager = rootview.findViewById<View>(R.id.masterViewPager) as ViewPager

        return rootview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = getViewModel(this, viewModelFactory)
        viewModel.getBooks()
        viewModel.getBookClicked()
        viewModel.getChapterClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBooksResult.observe(this, Observer {
            it?.let { books ->
                val adapter = TabbedDialogAdapter(childFragmentManager)

                adapter.addFragment("BOOK", FragmentBook.createInstance(books))
                adapter.addFragment("CHAPTER", FragmentChapter.createInstance(books))
                adapter.addFragment("VERSE", FragmentVerse.createInstance(40))

                adapter.notifyDataSetChanged()
                viewPager.apply {
                    this.adapter = adapter
                }
                tabLayout.apply {
                    setupWithViewPager(viewPager)
                }
            }
        })

        viewModel.clickedBook.observe(this, Observer {
            it?.let { bookEvent ->
                bookname = bookEvent.name
                viewPager.setCurrentItem(1, true)
                dialog.setTitle(bookname)
            }
        })

        viewModel.clickedChapter.observe(this, Observer {
            it?.let { chapterEvent ->
                chapter = chapterEvent.position
                dialog.setTitle("$bookname ${chapter + 1}")
                viewPager.setCurrentItem(2, true)
            }
        })

        viewModel.clickedVerse.observe(this, Observer {
            it?.let { verseEvent ->
                verse = verseEvent.position
                dialog.setTitle("$bookname ${chapter + 1} ${verse + 1}")
                this.dialog.dismiss()
            }
        })
    }

    /** The system calls this only when creating the layout in a dialog.  */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        return dialog
    }

}