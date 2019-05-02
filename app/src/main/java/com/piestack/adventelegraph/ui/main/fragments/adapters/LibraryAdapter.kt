/*
 * Copyright (c) 2019. Jeffrey Nyauke.
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

package com.piestack.adventelegraph.ui.main.fragments.adapters


import android.app.Activity
import android.os.Environment
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.ui.main.interfaces.MediaClickListener
import com.piestack.adventelegraph.models.Media
import timber.log.Timber
import java.io.File

/**
 * Created by Jeffrey Nyauke on 11/7/2015.
 */
class LibraryAdapter(private val activity: Activity, private val books: MutableList<Media>, private val mediaClickListener: MediaClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(viewGroup.context)

        val viewBook = inflater.inflate(R.layout.item_book, viewGroup, false)
        viewHolder = ViewHolderBook(viewBook)

        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewHolderMedia = viewHolder as ViewHolderBook
        configureMusicView(viewHolderMedia, position)
        val media = books[position]
        viewHolderMedia.itemView.setOnClickListener { mediaClickListener.onMediaItemClick(position, media) }


    }

    private fun configureMusicView(viewHolderBook: ViewHolderBook, position: Int) {
        try {
            val book = books[position]
            viewHolderBook.titleTextView.text = book.title
            /*Glide.with(activity).load(R.drawable.about_page_button_dark)
                .thumbnail(0.2f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolderBook.getImageView());*/
            val filePath = Environment.getExternalStorageDirectory().toString() + "/Advent Telegraph/" + book.url
            Timber.e(filePath)
            val file = File(filePath)
            if (!file.exists()) {
                //viewHolderBook.drawable.setImageResource(R.drawable.ic_file_download_black_24dp)
                viewHolderBook.descriptionTextView.text = "Download " + book.published_date + " " + book.size
            } else {
                viewHolderBook.descriptionTextView.text = "Play " + book.published_date + " " + book.size
                //viewHolderBook.drawable.setImageResource(R.drawable.ic_open)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun getItemCount(): Int {
        return books.size
    }


    fun refillAdapter(media: Media) {

        /*add new message chat to list*/
        books.add(media)

        /*refresh view*/
        notifyItemInserted(itemCount - 1)
    }

    fun refillAdapter(listMusic: List<Media>) {

        books.addAll(listMusic)
        /*refresh view*/
        notifyDataSetChanged()
    }

    fun removeAdapter(media: Media) {

        /*add new message chat to list*/
        val index = books.indexOf(media)
        books.remove(media)
        /*refresh view*/
        notifyItemRemoved(index)
    }

    fun refillFirsTimeAdapter(listMusic: List<Media>) {

        /*add new message chat to list*/
        books.clear()
        books.addAll(listMusic)
        notifyDataSetChanged()
        /*refresh view*/
        //notifyItemInserted(itemCount - 1)
    }

    fun cleanUp() {
        books.clear()
        notifyDataSetChanged()
    }



    /*==============ViewHolder===========*/

    /*ViewHolder for Music*/

    inner class ViewHolderBook(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        val imageView: ImageView = itemView.findViewById(R.id.image)
        //val drawable = itemView.findViewById<ImageView>(R.id.drawable)

    }


}
