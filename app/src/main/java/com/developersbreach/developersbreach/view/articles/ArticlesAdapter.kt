package com.developersbreach.developersbreach.view.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.developersbreach.developersbreach.databinding.ItemArticlesBinding
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.view.articles.ArticlesAdapter.*
import com.developersbreach.developersbreach.viewModel.ArticlesViewModel

class ArticlesAdapter(
    private val viewModel: ArticlesViewModel,
    private val fragment: ArticlesFragment
) :
    ListAdapter<Articles, ArticlesViewHolder>(Articles.DiffCallback) {

    class ArticlesViewHolder(
        private var binding:
        ItemArticlesBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            articles: Articles,
            viewModel: ArticlesViewModel,
            fragment: ArticlesFragment
        ) {
            binding.article = articles
            binding.viewModel = viewModel
            binding.fragment = fragment
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            ItemArticlesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val articles: Articles = getItem(position)
        holder.bind(articles, viewModel, fragment)
    }
}
