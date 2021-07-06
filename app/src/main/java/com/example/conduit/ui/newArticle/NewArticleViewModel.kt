package com.example.conduit.ui.newArticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.ConduitClient
import com.example.api.models.request.CreateArticleRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class NewArticleViewModel : ViewModel() {

    private val authApi = ConduitClient.authApi

    fun uploadArticle(articleRequest: CreateArticleRequest) =
        viewModelScope.async(Dispatchers.IO){
            return@async authApi.createArticle(articleRequest)
    }

}