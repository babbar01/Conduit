package com.example.conduit.ui.newArticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.conduit.R

class NewArticleFragment : Fragment() {

    private lateinit var newArticleViewModel: NewArticleViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        newArticleViewModel =
                ViewModelProvider(this).get(NewArticleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new_article, container, false)


        return root
    }
}