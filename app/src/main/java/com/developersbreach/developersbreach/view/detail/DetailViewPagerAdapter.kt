package com.developersbreach.developersbreach.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.developersbreach.developersbreach.databinding.ItemViewPagerBinding
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.view.detail.DetailViewPagerAdapter.*


class DetailViewPagerAdapter :
    ListAdapter<Articles, DetailViewHolder>(Articles.DiffCallback) {

    class DetailViewHolder(
        private var binding: ItemViewPagerBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            articles: Articles
        ) {
            binding.article = articles
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemViewPagerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val articles: Articles = getItem(position)
        holder.bind(articles)
    }
}