package com.example.conduit.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.ConduitClient
import com.example.api.models.entity.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritedArticlesViewModel : ViewModel() {

    val authApi = ConduitClient.authApi

    val favouritedArticlesList = MutableLiveData(emptyList<Article>())

    fun fetchFavouritedArticleList(username : String) = viewModelScope.launch(Dispatchers.IO) {
        val articlesResponse = authApi.getArticles(favourited = username)
        articlesResponse.body()?.articles.let { favouritedArticlesList.postValue(it) }
    }

}