package com.example.conduit.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.conduit.databinding.FragmentGlobalFeedBinding

class GlobalFeedFragment : Fragment() {

    private var _binding : FragmentGlobalFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGlobalFeedBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val adapter = ArticleAdapter()
        binding.recyclerViewGlobalFeed.adapter = adapter

        viewModel.fetchGlobalFeed()

        viewModel.globalFeedList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}