package com.piestack.adventelegraph.ui.bible.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.app.MyApplication
import com.piestack.adventelegraph.di.module.DatabaseModule
import com.piestack.adventelegraph.models.room.Book
import com.piestack.adventelegraph.models.room.VerseRef
import com.piestack.adventelegraph.models.room.kjv
import com.piestack.adventelegraph.ui.bible.BibleActivityViewModel
import com.piestack.adventelegraph.ui.bible.BibleActivityViewModelFactory
import com.piestack.adventelegraph.ui.bible.BibleAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_bible.view.*
import timber.log.Timber
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class BibleFragment : Fragment() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    @Inject
    lateinit var bibleActivityViewModelFactory: BibleActivityViewModelFactory
    private lateinit var bibleActivityViewModel: BibleActivityViewModel
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var verses: ArrayList<kjv>
    private lateinit var adapter: BibleAdapter

    private var verseRef = VerseRef()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_bible, container, false)
        MyApplication.getComponent(requireContext())?.getDatabaseComponent(DatabaseModule(requireContext()))?.inject(this)
        bibleActivityViewModel = ViewModelProviders.of(this, bibleActivityViewModelFactory).get(BibleActivityViewModel::class.java)

        layoutManager = LinearLayoutManager(requireContext(), LinearLayout.VERTICAL, false)
        v.recycler.layoutManager = layoutManager

        verses = ArrayList()

        adapter = BibleAdapter(verses)

        v.recycler.adapter = adapter

        mListener.onNewPage(verseRef)

        compositeDisposable.add(bibleActivityViewModel.getChapter(verseRef.chapter, verseRef.book)
                .subscribeBy { chapterVerses ->
                    verses = chapterVerses
                    adapter.refill(verses)
                    layoutManager.scrollToPositionWithOffset(verseRef.verse - 1, 0)
                    try {
                        Timber.e(verses.size.toString())
                        Timber.e(verses[0].t)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                })
        return v
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

                if (position + 1 > cumulative) continue
                else {
                    fragment.verseRef.book = bookNo
                    fragment.verseRef.chapter = (position + 1) - (cumulative - book.NumOfChapters)
                    fragment.verseRef.verse = verse
                    Timber.e("Else: $position ${fragment.verseRef.book} ${fragment.verseRef.chapter} ${fragment.verseRef.verse}")
                    break
                }
            }

            return fragment
        }
    }

    interface FragmentBibleListener {
        fun onNewPage(verseRef: VerseRef)
    }

    lateinit var mListener: FragmentBibleListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mListener = context as FragmentBibleListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + " must implement FragmentVerseListener")
        }
    }
}
