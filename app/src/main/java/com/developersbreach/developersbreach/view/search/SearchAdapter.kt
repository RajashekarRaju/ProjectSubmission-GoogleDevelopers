package com.developersbreach.developersbreach.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.developersbreach.developersbreach.databinding.ItemSearchBinding
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.view.search.SearchAdapter.*

class SearchAdapter(
    private val fragment: SearchFragment
) :
    ListAdapter<Articles, SearchViewHolder>(Articles.DiffCallback) {

    class SearchViewHolder(
        private var binding: ItemSearchBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            articles: Articles,
            fragment: SearchFragment
        ) {
            binding.article = articles
            binding.fragment = fragment
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val articles: Articles = getItem(position)
        holder.bind(articles, fragment)
    }
}
