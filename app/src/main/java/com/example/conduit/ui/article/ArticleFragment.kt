package com.example.conduit.ui.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.api.models.entity.AddCommentCreds
import com.example.api.models.entity.Article
import com.example.conduit.databinding.FragmentArticleBinding
import com.example.conduit.ui.AuthViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleFragment : Fragment() {

    companion object{
        const val CHIP_FAVOURITE_STRING = "favourite"
        const val CHIP_UNFAVOURITE_STRING = "unfavourite"
        const val CHIP_FOLLOW_STRING = "follow user"
        const val CHIP_FOLLOWING_STRING = "following user"
    }

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var article : Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArticleBinding.inflate(inflater,container,false)
        articleViewModel = ViewModelProvider(this)[ArticleViewModel::class.java]

        setArticleInLayout()

        binding.btnAddComment.setOnClickListener {
            Toast.makeText(context,"${article.slug}",Toast.LENGTH_LONG).show()
            val commentBody = binding.editTextComment.text.toString()
            Log.d(CHIP_FAVOURITE_STRING, "onCreateView: $commentBody")
            val deffered = articleViewModel.addComment(article.slug, AddCommentCreds(body = commentBody))
            lifecycleScope.launch(Dispatchers.IO) {
                deffered.await()
                articleViewModel.fetchCommentList(article.slug)
            }
            binding.editTextComment.text?.clear()
            binding.editTextComment.clearFocus()
        }

        val commentsAdapter = CommentAdapter()

        binding.recylerViewComments.apply {
            adapter = commentsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        articleViewModel.commentList.observe(viewLifecycleOwner){
            commentsAdapter.submitList(it)
        }

        return binding.root
    }

    private fun setFavouriteBtnOnClick(article : Article) {
        binding.chipFavouriteArticle.setOnClickListener {
            if(binding.chipFavouriteArticle.text == CHIP_FAVOURITE_STRING){
                binding.chipFavouriteArticle.text = CHIP_UNFAVOURITE_STRING
                val deferredResult = articleViewModel.favouriteArticle(article.slug)
                lifecycleScope.launch {
                    val response = deferredResult.await()
                    if(response.errorBody() != null){
                        binding.chipFavouriteArticle.text = CHIP_FAVOURITE_STRING
                        Toast.makeText(context,"${response.errorBody()}",Toast.LENGTH_LONG).show()
                    }
                }
            }
            else {
                binding.chipFavouriteArticle.text = CHIP_FAVOURITE_STRING
                val deferredResult = articleViewModel.unfavouriteArticle(article.slug)
                lifecycleScope.launch {
                    val response = deferredResult.await()
                    if(response.errorBody() != null){
                        binding.chipFavouriteArticle.text = CHIP_UNFAVOURITE_STRING
                        Toast.makeText(context,"${response.errorBody()}",Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    private fun setFollowBtnOnClick(article : Article) {
        binding.chipFollowUser.setOnClickListener {
            if(binding.chipFollowUser.text == CHIP_FOLLOW_STRING){
                binding.chipFollowUser.text = CHIP_FOLLOWING_STRING
                val deferredResult = articleViewModel.followUser(article.author.username)
                lifecycleScope.launch {
                    val response = deferredResult.await()
                    if(!response.isSuccessful){
                        binding.chipFollowUser.text = CHIP_FOLLOW_STRING
                        Toast.makeText(context,"${response.errorBody()}",Toast.LENGTH_LONG).show()
                    }
                }
            }
            else {
                binding.chipFollowUser.text = CHIP_FOLLOW_STRING
                val deferredResult = articleViewModel.unfollowUser(article.author.username)
                lifecycleScope.launch {
                    val response = deferredResult.await()
                    if(!response.isSuccessful){
                        binding.chipFollowUser.text = CHIP_FOLLOWING_STRING
                        Toast.makeText(context,"${response.errorBody()}",Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    private fun setArticleInLayout() {
        authViewModel.currentArticle.observe(viewLifecycleOwner) { article ->
            this.article = article
            updateArticleComments(article)
            article?.let {



                setFavouriteBtnOnClick(it)
                setFollowBtnOnClick(it)

                binding.apply {
                    chipFavouriteArticle.text = if(it.favorited) CHIP_UNFAVOURITE_STRING
                        else CHIP_FAVOURITE_STRING

                    chipFollowUser.text = if(it.author.following) CHIP_FOLLOWING_STRING
                        else CHIP_FOLLOW_STRING

                    articleTitle.text = it.title
                    authorTextViewArticleFragment.text = it.author.username
                    // TODO: 03-05-2021 convert into proper format
                    dateTextViewArticleFragment.text = it.createdAt

                    it.author.image?.let { url ->
                        Glide.with(this@ArticleFragment)
                            .load(url)
                            .into(avatarImageViewArticleFragment)
                    }

                    webViewArticleBody.loadDataWithBaseURL(
                        "",
                        it.body,
                        "text/html",
                        "UTF-8",
                        ""
                    )

                    for (tag in it.tagList) {
                        val chip = Chip(context)
                        chip.text = tag
                        binding.chipGroupTags.addView(chip)
                    }

                }
            }
        }
    }

    private fun updateArticleComments(article: Article) {
        articleViewModel.fetchCommentList(article.slug)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}