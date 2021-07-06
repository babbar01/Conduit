package com.example.conduit.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.ConduitClient
import com.example.api.models.entity.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyArticleViewModel : ViewModel() {

    val authApi = ConduitClient.authApi

    val myArticlesList = MutableLiveData(emptyList<Article>())

    fun fetchMyArticleList(username : String) = viewModelScope.launch(Dispatchers.IO) {
        val articlesResponse = authApi.getArticles(author = username)
        articlesResponse.body()?.articles.let { myArticlesList.postValue(it) }
    }

}