package com.piestack.adventelegraph.ui

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.app.Config
import com.piestack.adventelegraph.models.Post
import kotlinx.android.synthetic.main.activity_quote.*
import org.parceler.Parcels

class Quote : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)

        val extras = intent.extras
        val post = Parcels.unwrap<Post>(intent.getParcelableExtra<Parcelable>(Config.AD_POST))

        title_quote?.text = post.getTitle()
        text_qoute?.text = post.getText()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (extras?.getString(Config.POST_TRANSITION_TEXT) != null) {
                val titleTransitionName = extras.getString(Config.POST_TRANSITION_TITLE)
                val textTransitionName = extras.getString(Config.POST_TRANSITION_TEXT)
                title_quote.transitionName = titleTransitionName
                text_qoute.transitionName = textTransitionName
            }

        }
    }
}
