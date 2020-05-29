package com.developersbreach.developersbreach.view.favorites

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.viewModel.FavoritesViewModel


@BindingAdapter("favoriteToDetailListener")
fun bindFavoriteToDetailClickListener(
    textView: TextView,
    article: Articles
) {
    textView.setOnClickListener { view: View ->
        val direction: NavDirections = FavoritesFragmentDirections.FavoritesToDetailFragment(article)
        Navigation.findNavController(view).navigate(direction)
    }
}


@BindingAdapter("favoriteFragmentModel", "favoriteViewModel")
fun bindRemoveFavoriteListener(
    imageView: ImageView,
    article: Articles,
    viewModel: FavoritesViewModel
) {
    imageView.setImageResource(R.drawable.ic_favorite_remove)

    imageView.setOnLongClickListener {
        viewModel.deleteAllArticles()
        true
    }

    imageView.setOnClickListener {
        viewModel.deleteArticle(article)
    }
}