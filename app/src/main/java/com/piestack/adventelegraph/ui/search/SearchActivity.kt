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

package com.piestack.adventelegraph.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import com.google.android.material.appbar.AppBarLayout
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.di.ViewModelFactory
import com.piestack.adventelegraph.models.Post
import com.piestack.adventelegraph.ui.base.BaseThemedActivity
import com.piestack.adventelegraph.ui.main.fragments.adapters.PostAdapter
import com.piestack.adventelegraph.ui.main.interfaces.PostClickListener
import com.piestack.adventelegraph.util.getViewModel
import com.piestack.adventelegraph.util.vertical
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : BaseThemedActivity(), PostClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SearchActivityViewModel

    private lateinit var searchQuery: CharSequence

    private var postAdapter: PostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { bar, offset ->
            bar.isActivated = offset < 0
        })

        viewModel = getViewModel(this, viewModelFactory)
        postAdapter = PostAdapter(this, mutableListOf(), this)




        resultsListView.apply {
            vertical()
            //adapter = listAdapter
        }
    }

    override fun onAdClick(pos: Int, post: Post, shareTitleView: View, shareTextView: View, transitionName: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPostClick(pos: Int, post: Post) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPostItemClick(pos: Int, post: Post, shareImageView: View, shareWebView: View, transitionName: String) {
         //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPostNormalItemClick(pos: Int, post: Post, transitionName: String) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchIcon = menu?.findItem(R.id.action_search)
        val searchView = searchIcon?.actionView as SearchView

        searchIcon.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                onBackPressed()
                return false
            }

        })
        searchIcon.expandActionView()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                //viewModel.search(query)
                searchQuery = query ?: ""
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}
