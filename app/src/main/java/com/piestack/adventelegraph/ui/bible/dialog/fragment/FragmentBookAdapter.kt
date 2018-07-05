package com.piestack.adventelegraph.ui.bible.dialog.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.models.room.Book


class FragmentBookAdapter(private val mContext: Context, private val list: ArrayList<Book>) : BaseAdapter() {

    override fun getCount(): Int = list.size

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
            textView.text = list[position].ShortName

        } else {
            gridView = convertView
            textView = gridView
                    .findViewById(R.id.text) as TextView
            textView.text = list[position].ShortName
        }

        return gridView
    }
}