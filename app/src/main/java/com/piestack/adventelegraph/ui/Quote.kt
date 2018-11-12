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

package com.piestack.adventelegraph.ui

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.app.Config
import com.piestack.adventelegraph.models.Post
import kotlinx.android.synthetic.main.activity_quote.*

class Quote : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)

        val extras = intent.extras
        val post: Post = intent.getParcelableExtra(Config.AD_POST)

        title_quote?.text = post.title
        text_qoute?.text = post.lede

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
