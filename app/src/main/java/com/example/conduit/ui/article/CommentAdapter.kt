package com.example.conduit.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.api.models.entity.Article
import com.example.api.models.entity.Comment
import com.example.conduit.R
import com.example.conduit.databinding.ItemCommentListBinding

class CommentAdapter : ListAdapter<Comment,CommentAdapter.CommentViewHolder>(
    object : DiffUtil.ItemCallback<Comment>(){
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }
) {

    class CommentViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView){

        val binding = ItemCommentListBinding.bind(itemView)

        fun bind(comment: Comment) {
            binding.apply {
                authorTextView.text = comment.author.username
                dateTextView.text = comment.createdAt
                bodyTextView.text = comment.body
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_list,parent,false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }
}