package com.piestack.adventelegraph.ui.bible.dialog.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.models.room.Book
import kotlinx.android.synthetic.main.fragment_book_sample.view.*
import timber.log.Timber

class FragmentChapter : Fragment(), FragmentBook.FragmentBookListener {
    override fun onBookClick(position: Int, name: String, chapters: Int) {
        book = position + 1
        mAdapter = FragmentChapterAdapter(requireActivity(), books[book].NumOfChapters)
        mAdapter.notifyDataSetChanged()
        Timber.e("Update books")
    }

    private lateinit var books: ArrayList<Book>
    private var book: Int = 0
    private lateinit var mAdapter: FragmentChapterAdapter
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = activity?.layoutInflater!!.inflate(R.layout.fragment_book_sample, container, false)

        mAdapter = FragmentChapterAdapter(requireActivity(), books[book].NumOfChapters)

        v.gridview.adapter = mAdapter

        v.gridview.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    mListener.onChapterClick(position)
                }

        return v
    }

    companion object {
        fun createInstance(book: Int, books: ArrayList<Book>): FragmentChapter {
            val fragment = FragmentChapter()
            fragment.books = books
            fragment.book = book
            return fragment
        }
    }

    fun updateChapters(int: Int) {
        book = int
        mAdapter.notifyDataSetChanged()
        Toast.makeText(requireActivity(), "Book", Toast.LENGTH_SHORT).show()
    }

    interface FragmentChapterListener {
        fun onChapterClick(position: Int)
    }

    lateinit var mListener: FragmentChapterListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mListener = context as FragmentChapterListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + " must implement FragmentChapterListener")
        }
    }
}