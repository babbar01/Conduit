package com.example.conduit.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.conduit.R
import com.example.conduit.databinding.FragmentGlobalFeedBinding
import com.example.conduit.ui.AuthViewModel

class GlobalFeedFragment : Fragment() {

    private var _binding: FragmentGlobalFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGlobalFeedBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val adapter = ArticleAdapter { article ->

            authViewModel.currentArticle.postValue(article)

            activity?.findNavController(R.id.nav_host_fragment)
                ?.navigate(R.id.action_navigation_home_to_articleFragment)
        }

        binding.recyclerViewGlobalFeed.adapter = adapter

        viewModel.fetchGlobalFeed()

        viewModel.globalFeedList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}