package com.developersbreach.developersbreach.view.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.developersbreach.developersbreach.databinding.ItemFavoritesBinding
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.viewModel.FavoritesViewModel


class FavoritesAdapter(
    private val viewModel: FavoritesViewModel,
    private val fragment: FavoritesFragment
) :
    ListAdapter<Articles,
            FavoritesAdapter.FavoritesViewHolder>(Articles.DiffCallback) {

    class FavoritesViewHolder(
        private var binding:
        ItemFavoritesBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            articles: Articles,
            viewModel: FavoritesViewModel,
            fragment: FavoritesFragment
        ) {
            binding.articles = articles
            binding.viewModel = viewModel
            binding.fragment = fragment
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            ItemFavoritesBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val articles: Articles = getItem(position)
        holder.bind(articles, viewModel, fragment)
    }
}