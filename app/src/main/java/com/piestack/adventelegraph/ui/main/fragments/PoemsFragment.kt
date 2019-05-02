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

package com.piestack.adventelegraph.ui.main.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piestack.adventelegraph.R
import com.piestack.adventelegraph.app.Config
import com.piestack.adventelegraph.di.ViewModelFactory
import com.piestack.adventelegraph.models.Post
import com.piestack.adventelegraph.ui.PostDetailActivity
import com.piestack.adventelegraph.ui.Quote
import com.piestack.adventelegraph.ui.main.fragments.adapters.PostAdapter
import com.piestack.adventelegraph.ui.main.interfaces.PostClickListener
import com.piestack.adventelegraph.util.*
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject


/**
 * Created by Jeffrey Nyauke on 1/31/2017.
 */

class PoemsFragment : Fragment(), PostClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: PoemsFragmentViewModel

    private var postAdapter: PostAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(this, viewModelFactory)
        postAdapter = PostAdapter(requireActivity(), mutableListOf(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.fragment_recycler.apply {
            vertical()
            itemAnimator = DefaultItemAnimator()
            adapter = postAdapter
        }

        viewModel.getArticles().observe(this, Observer { articles ->

            if (articles != null) {
                if(setup.isVisible()){
                    setup.hide()
                    fragment_recycler.show()

                }
                postAdapter?.refillFirsTimeAdapter(articles)
            }

            var isScrolling = false
            var isLastItemReached = false

            val onScrollListener = object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                        isScrolling = true
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                    val visibleItemCount = linearLayoutManager.childCount
                    val totalItemCount = linearLayoutManager.itemCount

                    if (isScrolling && (firstVisibleItemPosition + visibleItemCount == totalItemCount && !isLastItemReached)) {
                        isScrolling = false
                        viewModel.getArticles().observe(requireActivity(), Observer { articles ->
                            postAdapter!!.refillAdapter(articles)
                            if (viewModel.articleCount < Config.FETCH_LIMIT) {
                                isLastItemReached = true
                            }
                        })
                    }
                }
            }

            view.fragment_recycler.setOnScrollListener(onScrollListener)
        })
    }

    override fun onPostItemClick(pos: Int, post: Post, shareImageView: View, shareTextView: View, transitionName: String) {
        val intent = Intent(activity, PostDetailActivity::class.java)
        intent.putExtra(Config.POST_URL, post)
        intent.putExtra(Config.POST_TRANSITION_IMAGE, transitionName)
        intent.putExtra(Config.POST_TRANSITION_TEXT, transitionName + "w")

        val params1 = Pair(shareImageView, transitionName)
        val params2 = Pair(shareTextView, transitionName + "w")


        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(), params1, params2
        )

        startActivity(intent, options.toBundle())
    }

    override fun onPostNormalItemClick(pos: Int, post: Post, transitionName: String) {
        val intent = Intent(activity, PostDetailActivity::class.java)
        intent.putExtra(Config.POST_URL, post)
        intent.putExtra(Config.POST_TRANSITION_IMAGE, transitionName)

        startActivity(intent)
    }

    override fun onPostClick(pos: Int, post: Post) {
        val intent = Intent(activity, PostDetailActivity::class.java)
        intent.putExtra(Config.POST_URL, post)
        startActivity(intent)
    }

    override fun onAdClick(pos: Int, post: Post, shareTitleView: View, shareTextView: View, transitionName: String) {
        val intent = Intent(activity, Quote::class.java)
        intent.putExtra(Config.AD_POST, post)
        intent.putExtra(Config.POST_TRANSITION_TITLE, transitionName)
        intent.putExtra(Config.POST_TRANSITION_TEXT, transitionName)

        val params1 = Pair(shareTitleView, transitionName)
        val params2 = Pair(shareTextView, transitionName)


        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(), params1, params2
        )

        startActivity(intent, options.toBundle())
    }

}