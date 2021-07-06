package com.example.conduit.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.api.models.entity.Article
import com.example.conduit.R
import com.example.conduit.databinding.FragmentGlobalFeedBinding
import com.example.conduit.databinding.ItemArticlesListBinding

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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemArticlesListBinding.bind(view)
        fun bind(article: Article) {
            binding.apply {
                titleTextView.text = article.title
                authorTextView.text = article.author.username
                dateTextView.text = article.createdAt
                bodyTextView.text = article.body
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