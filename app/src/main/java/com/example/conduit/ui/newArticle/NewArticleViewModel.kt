package com.example.conduit.ui.newArticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewArticleViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is new Article Fragment"
    }
    val text: LiveData<String> = _text
}