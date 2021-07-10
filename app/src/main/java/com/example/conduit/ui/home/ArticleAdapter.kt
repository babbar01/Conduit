package com.example.conduit.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.api.models.entity.Article
import com.example.conduit.R
import com.example.conduit.databinding.FragmentGlobalFeedBinding
import com.example.conduit.databinding.ItemArticlesListBinding
import kotlin.random.Random

class ArticleAdapter(
    val onClickArticle : (article : Article) -> Unit
) : ListAdapter<Article, ArticleAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }
) {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemArticlesListBinding.bind(view)
        fun bind(article: Article) {
            binding.apply {
                titleTextView.text = article.title
                authorTextView.text = article.author.username
                dateTextView.text = article.createdAt
                bodyTextView.text = article.description
                if(!article.author.image.isNullOrEmpty()){
                    Glide.with(view).load(article.author.image).into(avatarImageView)
                }

                val drawableList = listOf(R.drawable.vector_random1,
                    R.drawable.vector_random2,
                    R.drawable.vector_random3,
                    R.drawable.vector_random4,
                    R.drawable.vector_random5,
                    R.drawable.vector_random6,
                    R.drawable.vector_random7,
                    R.drawable.vector_random8,
                    R.drawable.vector_random9)
                binding.profileImageView.setImageResource(drawableList.random())
            }
        }

        fun setOnArticleClicked(article: Article) = binding.root.setOnClickListener {
            onClickArticle(article)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_articles_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
        holder.setOnArticleClicked(article)
    }

}