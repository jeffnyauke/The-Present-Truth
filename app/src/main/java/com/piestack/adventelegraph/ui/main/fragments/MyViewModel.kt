package com.piestack.adventelegraph.ui.main.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.piestack.adventelegraph.models.Post

class MyViewModel : ViewModel() {
    private lateinit var articles: MutableLiveData<List<Post>>
    fun getArticles(): LiveData<List<Post>> {
        if (articles == null) {
            articles = MutableLiveData()
            loadArticles()
        }
        return articles
    }

    private fun loadArticles() {
        // do async operation to fetch articles
    }
}