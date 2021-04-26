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

    var globalFeedList  = MutableLiveData<List<Article>>()
    var myFeedList  = MutableLiveData<List<Article>>()

    fun fetchGlobalFeed() = viewModelScope.launch {
        val articlesResponse = basicApi.getArticles()
        globalFeedList.postValue(articlesResponse.body()?.articles)
    }

    fun fetchMyFeed() = viewModelScope.launch {
        val articlesResponse = authApi.getFeedArticles()
        myFeedList.postValue(articlesResponse.body()?.articles)
    }


}