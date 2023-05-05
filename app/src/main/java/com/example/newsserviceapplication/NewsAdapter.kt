package com.example.newsserviceapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsserviceapplication.databinding.ItemNewsBinding

class NewsAdapter(private val context: Context, private val articles: List<Article>,
private val listener: ItemClickListener):
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: ItemNewsBinding):
        RecyclerView.ViewHolder(binding.root){
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding: ItemNewsBinding= ItemNewsBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        val viewHolder= NewsViewHolder(binding)
        binding.root.setOnClickListener {
            listener.onItemClick(articles[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.article= articles[position]
        holder.binding.share.setOnClickListener {
            listener.onShareClicked(articles[position])
        }

        holder.binding.executePendingBindings()
    }
}

interface ItemClickListener{
    fun onItemClick(item: Article)
    fun onShareClicked(item: Article)
}