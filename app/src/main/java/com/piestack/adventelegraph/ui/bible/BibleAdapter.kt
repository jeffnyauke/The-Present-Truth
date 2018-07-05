package com.piestack.adventelegraph.ui.bible

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.models.room.kjv

/**
 * Created by Jeffrey Nyauke on 12/17/2017.
 * Piestack.
 */
class BibleAdapter(private val verses: ArrayList<kjv>) : RecyclerView.Adapter<BibleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_verse_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(verses[position])
    }

    override fun getItemCount(): Int {
        return verses.size
    }

    fun refill(list: ArrayList<kjv>) {
        verses.clear()
        verses.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(kjv: kjv) {
            val textViewVerse = itemView.findViewById<TextView>(R.id.verse)
            textViewVerse.text = "${kjv.v}  ${kjv.t}"
        }
    }
}