package com.example.conduit.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.conduit.R
import com.example.conduit.databinding.FragmentGlobalFeedBinding
import com.example.conduit.ui.home.dummy.DummyContent

class GlobalFeedFragment : Fragment() {

    private var _binding : FragmentGlobalFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGlobalFeedBinding.inflate(inflater)
        val adapter = ArticleAdapter(DummyContent.ITEMS)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}