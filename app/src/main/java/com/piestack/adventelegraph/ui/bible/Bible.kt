package com.piestack.adventelegraph.ui.bible

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.app.MyApplication
import com.piestack.adventelegraph.di.module.DatabaseModule
import com.piestack.adventelegraph.models.room.Book
import com.piestack.adventelegraph.models.room.VerseRef
import com.piestack.adventelegraph.ui.bible.dialog.TabbedDialog
import com.piestack.adventelegraph.ui.bible.dialog.fragment.FragmentBook
import com.piestack.adventelegraph.ui.bible.dialog.fragment.FragmentChapter
import com.piestack.adventelegraph.ui.bible.dialog.fragment.FragmentVerse
import com.piestack.adventelegraph.ui.bible.fragment.BibleFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_bible.*
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber
import javax.inject.Inject


class Bible : AppCompatActivity(), BibleFragment.FragmentBibleListener, FragmentBook.FragmentBookListener, FragmentChapter.FragmentChapterListener, FragmentVerse.FragmentVerseListener {

    private val compositeDisposable by lazy { CompositeDisposable() }

    @Inject
    lateinit var bibleActivityViewModelFactory: BibleActivityViewModelFactory
    private lateinit var bibleActivityViewModel: BibleActivityViewModel

    private var verseRef = VerseRef()
    private lateinit var books: ArrayList<Book>

    private val dialog = TabbedDialog()
    private lateinit var menu: Menu

    private var NUM_PAGES = 1189
    private var mPagerAdapter: PagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bible)

        MyApplication.getComponent(this)?.getDatabaseComponent(DatabaseModule(this))?.inject(this)
        bibleActivityViewModel = ViewModelProviders.of(this, bibleActivityViewModelFactory).get(BibleActivityViewModel::class.java)

        setSupportActionBar(toolbar)
        supportActionBar?.title =
                this.resources.getString(R.string.bible)

        compositeDisposable.add(bibleActivityViewModel.getBooks()
                .subscribeBy { vitabu ->
                    books = vitabu
                    try {
                        Timber.e(books.size.toString())
                        Timber.e(books[0].BookName)

                        mPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
                        pager.adapter = mPagerAdapter
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                })

        // Get the intent, verify the action and get the query
        handleIntent(intent)
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

    override fun onBookClick(position: Int, name: String, chapters: Int) {
        verseRef.book = position + 1
        verseRef.bookNameFull = name
        dialog.viewPager.setCurrentItem(1, true)
    }

    override fun onChapterClick(position: Int) {
        verseRef.chapter = position + 1
        dialog.viewPager.setCurrentItem(2, true)
    }

    override fun onVerseClick(position: Int) {
        //verse = String.format("%03d", (position + 1))
        verseRef.verse = position + 1

        updatePager()

        this.dialog.dismiss()
    }

    override fun onNewPage(verseRef: VerseRef) {
        this.verseRef.book = verseRef.book
        this.verseRef.bookNameFull = books[verseRef.book - 1].BookName
        this.verseRef.chapter = verseRef.chapter
        this.verseRef.verse = verseRef.verse
        updateMenuTitles()
    }

    private fun updateMenuTitles() {
        val string = "${verseRef.bookNameFull} ${verseRef.chapter - 1}"
        val menuItem = menu.findItem(R.id.action_chapter)
        menuItem.title = string
    }

    private fun updatePager() {

        var pagerPosition = 0
        var currentBookChapters = 0

        for ((i, book) in books.withIndex()) {
            Timber.e("Pager iterations: $i")
            if (i < verseRef.book) {
                pagerPosition += book.NumOfChapters
                currentBookChapters = book.NumOfChapters
            } else break
        }

        pagerPosition += verseRef.chapter - currentBookChapters
        Timber.e("Pager position: $pagerPosition")

        pager.setCurrentItem(pagerPosition, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        compositeDisposable.dispose()
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

        override fun getCount(): Int {
            return NUM_PAGES
        }

        override fun getItem(position: Int): Fragment {
            bibleFragment = BibleFragment.createInstance(position, books, verseRef.verse)
            return bibleFragment
        }
    }
}
