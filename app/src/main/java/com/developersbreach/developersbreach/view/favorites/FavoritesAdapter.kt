package com.developersbreach.developersbreach.view.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.developersbreach.developersbreach.databinding.ItemFavoritesBinding
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.viewModel.FavoritesViewModel


class FavoritesAdapter(
    private val onClickListener: OnClickListener,
    private val viewModel: FavoritesViewModel
) :
    ListAdapter<Articles,
            FavoritesAdapter.FavoritesViewHolder>(DiffCallback) {

    class FavoritesViewHolder(
        private var binding:
        ItemFavoritesBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            articles: Articles,
            viewModel: FavoritesViewModel
        ) {
            binding.favorites = articles
            binding.viewModel = viewModel
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
        val articles = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(articles)
        }
        holder.bind(articles, viewModel)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Articles>() {

        override fun areItemsTheSame(
            oldItem: Articles,
            newItem: Articles
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Articles,
            newItem: Articles
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (articles: Articles) -> Unit) {
        fun onClick(articles: Articles) = clickListener(articles)
    }
}