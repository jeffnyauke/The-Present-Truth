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

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.piestack.adventelegraph.R

class FragmentVerseAdapter(private val mContext: Context, private var numofVerses: Int) : BaseAdapter() {

    override fun getCount(): Int = numofVerses

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0L

    // create a new ImageView for each item referenced by the Adapter
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        lateinit var textView: TextView
        val gridView: View

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.item_dialog_text, null)

            textView = gridView
                    .findViewById(R.id.text) as TextView
            textView.text = (position + 1).toString()

        } else {
            gridView = convertView
            textView = gridView
                    .findViewById(R.id.text) as TextView
            textView.text = (position + 1).toString()
        }

        return gridView
    }

    fun refillAdapter(numofVerses: Int) {
        this.numofVerses = numofVerses
        notifyDataSetChanged()
    }
}