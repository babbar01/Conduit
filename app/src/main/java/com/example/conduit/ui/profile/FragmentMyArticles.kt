package com.example.conduit.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.conduit.R
import com.example.conduit.databinding.FragmentMyArticlesBinding
import com.example.conduit.ui.AuthViewModel
import com.example.conduit.ui.home.ArticleAdapter


class FragmentMyArticles : Fragment() {

    private var _binding : FragmentMyArticlesBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()
    private lateinit var myArticleViewModel: MyArticleViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyArticlesBinding.inflate(inflater,container,false)
        myArticleViewModel = ViewModelProvider(this)[MyArticleViewModel::class.java]

        val mAdapter = ArticleAdapter { article ->

            authViewModel.currentArticle.postValue(article)

            activity?.findNavController(R.id.nav_host_fragment)
                ?.navigate(R.id.action_navigation_profile_to_articleFragment)
        }

        binding.recyclerViewMyArticles.adapter = mAdapter

        authViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let { myArticleViewModel.fetchMyArticleList(it.username) }
        }

        myArticleViewModel.myArticlesList.observe(viewLifecycleOwner){
            mAdapter.submitList(it)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}