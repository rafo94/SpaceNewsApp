package com.example.spacenewsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.entities.response.News
import com.example.spacenewsapp.R

class NewsAdapter(
        private var newsList: ArrayList<News>,
        private val itemClickListener: ((News) -> Unit)
) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    inner class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val newsImage: AppCompatImageView = itemView.findViewById(R.id.item_news_image)
        private val newsTitle: AppCompatTextView = itemView.findViewById(R.id.item_news_title)
        private val newsDesc: AppCompatTextView = itemView.findViewById(R.id.item_news_desc)

        fun bind(news: News) {
            newsImage.load(news.imageUrl)
            newsTitle.text = news.title
            newsDesc.text = news.summary

            itemView.setOnClickListener { itemClickListener.invoke(news) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsHolder(view)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    fun setNewData(newsList: List<News>) {
        this.newsList.clear()
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }

    fun clearData() {
        newsList.clear()
        notifyDataSetChanged()
    }
}