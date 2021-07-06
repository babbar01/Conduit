package com.example.conduit.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.api.models.entity.Article
import com.example.conduit.databinding.FragmentArticleBinding
import com.example.conduit.ui.AuthViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class ArticleFragment : Fragment() {

    companion object{
        const val CHIP_FAVOURITE_STRING = "favourite"
        const val CHIP_UNFAVOURITE_STRING = "unfavourite"
    }

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()
    private lateinit var articleViewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArticleBinding.inflate(inflater,container,false)
        articleViewModel = ViewModelProvider(this)[ArticleViewModel::class.java]

        setArticleInLayout()


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

    private fun setAddCommentBtnOnClick(article: Article) {
        binding.btnAddComment.setOnClickListener {
            // TODO: 04-05-2021 Start from here
        }
    }

    private fun setArticleInLayout() {
        authViewModel.currentArticle.observe(viewLifecycleOwner) { article ->
            article?.let {

                setFavouriteBtnOnClick(it)
                setAddCommentBtnOnClick(it)

                binding.apply {
                    chipFavouriteArticle.text = if(it.favorited) CHIP_UNFAVOURITE_STRING
                        else CHIP_FAVOURITE_STRING

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}