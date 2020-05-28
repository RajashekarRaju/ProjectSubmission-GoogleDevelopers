package com.developersbreach.developersbreach.view.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.developersbreach.developersbreach.databinding.ItemArticlesBinding
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.viewModel.ArticlesViewModel

class ArticlesAdapter(
    private val onClickListener: OnClickListener,
    private val viewModel: ArticlesViewModel,
    private val articlesFragment: ArticlesFragment
) :
    ListAdapter<Articles, ArticlesAdapter.ArticlesViewHolder>(DiffCallback) {

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
            binding.articles = articles
            binding.viewModel = viewModel
            binding.fragment = fragment
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            ItemArticlesBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val articles = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(articles)
        }
        holder.bind(articles, viewModel, articlesFragment)
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
