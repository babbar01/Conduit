package com.example.conduit.ui.article

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.ConduitClient
import com.example.api.models.entity.AddCommentCreds
import com.example.api.models.entity.Comment
import com.example.api.models.request.AddCommentRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private val authApi = ConduitClient.authApi
    private val tag = "articleViewModelDebugTag"

    fun addComment(slug : String,
                   comment : AddCommentCreds
    ) = viewModelScope.async(Dispatchers.IO) {
        val commentRequest = AddCommentRequest(comment)
        val response = authApi.addComment(slug,commentRequest)
        Log.d(tag, "addComment: ${response.isSuccessful}")
    }

    var commentList  = MutableLiveData<List<Comment>>(emptyList())

    fun fetchCommentList(slug: String){
        viewModelScope.launch {
            val response = authApi.getCommentsOnArticle(slug)
            Log.d("fetchComments", "fetchCommentListResponse: ${response.isSuccessful}")
            commentList.postValue(response.body()?.comments)
        }
    }

    fun favouriteArticle(slug : String) = viewModelScope.async(Dispatchers.IO) {
        return@async authApi.favouriteArticle(slug)
    }

    fun followUser(username : String) = viewModelScope.async(Dispatchers.IO){
        return@async authApi.followUser(username)
    }

    fun unfollowUser(username : String) = viewModelScope.async(Dispatchers.IO){
        return@async authApi.unfollowUser(username)
    }

    fun unfavouriteArticle(slug : String) = viewModelScope.async(Dispatchers.IO) {
        return@async authApi.unfavouriteArticle(slug)
    }





}