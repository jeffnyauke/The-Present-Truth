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

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.PagerAdapter
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.di.ViewModelFactory
import com.piestack.adventelegraph.models.event.VerseEvent
import com.piestack.adventelegraph.models.room.Book
import com.piestack.adventelegraph.models.room.VerseRef
import com.piestack.adventelegraph.ui.base.BaseThemedActivity
import com.piestack.adventelegraph.ui.bible.dialog.TabbedDialog
import com.piestack.adventelegraph.ui.bible.fragment.BibleFragment
import com.piestack.adventelegraph.util.getViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_bible.*
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber
import javax.inject.Inject


class Bible : BaseThemedActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: BibleActivityViewModel

    private var verseRef = VerseRef()
    private lateinit var books: ArrayList<Book>

    private val dialog = TabbedDialog()
    private var menu: Menu? = null

    private var mPagerAdapter: PagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bible)

        viewModel = getViewModel(this, viewModelFactory)
        viewModel.getBooks()
        viewModel.getNewPage()
        viewModel.getVerseClicked()

        initUI()

        viewModel.getBooksResult.observe(this, Observer {
            it?.let { allBooks ->
                books = allBooks
                mPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
                pager.apply {
                    adapter = mPagerAdapter
                }
            }
        })

        viewModel.newPage.observe(this, Observer {
            it?.let { newPageEvent ->
                this.verseRef.book = newPageEvent.book - 1
                this.verseRef.bookNameFull = books[newPageEvent.book - 1].BookName
                this.verseRef.chapter = newPageEvent.chapter
                this.verseRef.verse = newPageEvent.verse
                updateMenuTitles()
            }
        })

        viewModel.clickedVerse.observe(this, Observer {
            it?.let {
                updatePager(it)
            }
        })

        // Get the intent, verify the action and get the query
        handleIntent(intent)
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.title =
                this.resources.getString(R.string.bible)
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //doMySearch(query)
        }
    }

    private fun showEditDialog() {
        dialog.show(supportFragmentManager, "tag")
    }

    private fun updateMenuTitles() {
        val string = "${verseRef.bookNameFull} ${verseRef.chapter - 1}"
        val menuItem = menu?.findItem(R.id.action_chapter)
        menuItem?.title = string
    }

    private fun updatePager(verseEvent: VerseEvent) {

        var pagerPosition = 0
        var currentBookChapters = 0

        for ((i, book) in books.withIndex()) {
            Timber.e("Pager iterations: $i")
            if (i < verseEvent.book) {
                pagerPosition += book.NumOfChapters
                currentBookChapters = book.NumOfChapters
            } else break
        }

        pagerPosition += verseEvent.chapter - currentBookChapters
        Timber.e("Pager position: $pagerPosition")

        pager.setCurrentItem(pagerPosition, true)
    }

    override fun onBackPressed() {
        if (dialog.isVisible) dialog.dismiss()
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.bible, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == R.id.action_chapter) {
            showEditDialog()
            return true
        } else if (id == R.id.action_search) {
            onSearchRequested()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        /**
         * Return the number of views available.
         */
        lateinit var bibleFragment: BibleFragment
        private val numPages = 1189

        override fun getCount(): Int {
            return numPages
        }

        override fun getItem(position: Int): Fragment {
            bibleFragment = BibleFragment.createInstance(position, books, verseRef.verse)
            return bibleFragment
        }
    }
}
