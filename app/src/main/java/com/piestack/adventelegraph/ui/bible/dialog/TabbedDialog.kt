package com.piestack.adventelegraph.ui.bible.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.app.MyApplication
import com.piestack.adventelegraph.di.module.DatabaseModule
import com.piestack.adventelegraph.ui.bible.BibleActivityViewModel
import com.piestack.adventelegraph.ui.bible.BibleActivityViewModelFactory
import com.piestack.adventelegraph.ui.bible.dialog.fragment.FragmentBook
import com.piestack.adventelegraph.ui.bible.dialog.fragment.FragmentChapter
import com.piestack.adventelegraph.ui.bible.dialog.fragment.FragmentVerse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class TabbedDialog : DialogFragment(), FragmentBook.FragmentBookListener, FragmentChapter.FragmentChapterListener, FragmentVerse.FragmentVerseListener {

    private lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    private var book: Int = 0
    private var bookname: String = ""
    private var chapter: Int = 0
    private var verse: Int = 0
    private val compositeDisposable by lazy { CompositeDisposable() }

    @Inject
    lateinit var bibleActivityViewModelFactory: BibleActivityViewModelFactory
    private lateinit var bibleActivityViewModel: BibleActivityViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootview = activity?.layoutInflater?.inflate(R.layout.dialog_bible, container, false)

        this.setStyle(androidx.fragment.app.DialogFragment.STYLE_NO_FRAME, theme)

        tabLayout = rootview?.findViewById<View>(R.id.tabLayout) as TabLayout
        viewPager = rootview.findViewById<View>(R.id.masterViewPager) as ViewPager

        val adapter = TabbedDialogAdapter(childFragmentManager)

        MyApplication.getComponent(requireActivity())?.getDatabaseComponent(DatabaseModule(requireActivity()))?.inject(this)
        bibleActivityViewModel = ViewModelProviders.of(this, bibleActivityViewModelFactory).get(BibleActivityViewModel::class.java)

        compositeDisposable.add(bibleActivityViewModel.getBooks().subscribeBy { books ->
            adapter.addFragment("BOOK", FragmentBook.createInstance(1, books))
            adapter.addFragment("CHAPTER", FragmentChapter.createInstance(1, books))
            adapter.addFragment("VERSE", FragmentVerse.createInstance(23))
            adapter.notifyDataSetChanged()
        })


        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        return rootview
    }

    /** The system calls this only when creating the layout in a dialog.  */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onBookClick(position: Int, name: String, chapters: Int) {
        book = position
        bookname = name
        viewPager.setCurrentItem(1, true)
        dialog.setTitle("$bookname $chapter:$verse")
    }

    override fun onChapterClick(position: Int) {
        chapter = position
        viewPager.setCurrentItem(2, true)
    }

    override fun onVerseClick(position: Int) {
        //verse = String.format("%03d", (position + 1))
        verse = position
        this.dialog.dismiss()
    }
}