package com.example.conduit.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.api.ConduitClient
import com.example.conduit.R
import com.example.conduit.databinding.FragmentMyFeedBinding
import com.example.conduit.ui.AuthViewModel


class MyFeedFragment : Fragment() {

    private var _binding : FragmentMyFeedBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyFeedBinding.inflate(inflater)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val adapter = ArticleAdapter()
        binding.recyclerViewMyFeed.adapter = adapter

        ConduitClient.authToken
            .let {
            homeViewModel.fetchMyFeed()
        }

        homeViewModel.myFeedList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}