package com.example.conduit.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.ConduitClient
import com.example.api.models.request.AddCommentRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private val authApi = ConduitClient.authApi

    fun addComment(slug : String,
                   commentRequest: AddCommentRequest
    ) = viewModelScope.async(Dispatchers.IO) {
        return@async authApi.addComment(slug,commentRequest)
    }

    fun favouriteArticle(slug : String) = viewModelScope.async(Dispatchers.IO) {
        return@async authApi.favouriteArticle(slug)
    }

    fun unfavouriteArticle(slug : String) = viewModelScope.async(Dispatchers.IO) {
        return@async authApi.unfavouriteArticle(slug)
    }





}