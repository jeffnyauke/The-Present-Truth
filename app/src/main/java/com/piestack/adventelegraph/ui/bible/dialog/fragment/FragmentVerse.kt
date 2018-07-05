package com.piestack.adventelegraph.ui.bible.dialog.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.piestack.adventelegraph.R
import kotlinx.android.synthetic.main.fragment_book_sample.view.*

class FragmentVerse : Fragment() {
    private var chapter = 23
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = activity?.layoutInflater!!.inflate(R.layout.fragment_book_sample, container, false)

        v.gridview.adapter = FragmentVerseAdapter(requireActivity(), chapter)

        v.gridview.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    mListener.onVerseClick(position)
                }

        return v
    }

    companion object {
        fun createInstance(chapter: Int): FragmentVerse {
            val fragment = FragmentVerse()
            fragment.chapter = chapter
            return fragment
        }
    }

    interface FragmentVerseListener {
        fun onVerseClick(position: Int)
    }

    lateinit var mListener: FragmentVerseListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            mListener = context as FragmentVerseListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + " must implement FragmentVerseListener")
        }
    }
}