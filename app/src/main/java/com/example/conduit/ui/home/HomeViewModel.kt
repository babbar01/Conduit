package com.example.conduit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.ConduitClient
import com.example.api.models.entity.Article
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val basicApi = ConduitClient.basicApi
    private val authApi = ConduitClient.authApi

    var globalFeedList  = MutableLiveData(emptyList<Article>())
    var myFeedList  = MutableLiveData(emptyList<Article>())

    fun fetchGlobalFeed() = viewModelScope.launch {
        val articlesResponse = authApi.getArticles()
        articlesResponse.body()?.articles?.let {
            globalFeedList.postValue(it)
        }
    }

    fun fetchMyFeed() = viewModelScope.launch {
        val articlesResponse = authApi.getFeedArticles()
        articlesResponse.body()?.articles?.let {
            myFeedList.postValue(it)
        }
    }


}