package com.example.conduit.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.conduit.R
import com.example.conduit.databinding.FragmentFavouritedArticlesBinding
import com.example.conduit.databinding.FragmentMyArticlesBinding
import com.example.conduit.ui.AuthViewModel
import com.example.conduit.ui.home.ArticleAdapter


class FragmentFavouritedArticles : Fragment() {

    private var _binding : FragmentFavouritedArticlesBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()
    private lateinit var favouritedArticlesViewModel: FavoritedArticlesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavouritedArticlesBinding.inflate(inflater,container,false)
        favouritedArticlesViewModel = ViewModelProvider(this)[FavoritedArticlesViewModel::class.java]

        val mAdapter = ArticleAdapter { article ->

            authViewModel.currentArticle.postValue(article)

            activity?.findNavController(R.id.nav_host_fragment)
                ?.navigate(R.id.action_navigation_profile_to_articleFragment)
        }

        binding.recylerViewFavouritedArticles.adapter = mAdapter

        authViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let { favouritedArticlesViewModel.fetchFavouritedArticleList(it.username) }
        }

        favouritedArticlesViewModel.favouritedArticlesList.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.emptyLayout.visibility = View.VISIBLE
                binding.recylerViewFavouritedArticles.visibility = View.GONE
            }
            else {
                binding.emptyLayout.visibility = View.GONE
                binding.recylerViewFavouritedArticles.visibility = View.VISIBLE
            }
            mAdapter.submitList(it)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}