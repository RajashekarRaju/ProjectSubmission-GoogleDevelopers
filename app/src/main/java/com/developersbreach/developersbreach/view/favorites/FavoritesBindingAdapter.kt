package com.developersbreach.developersbreach.view.favorites

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.viewModel.FavoritesViewModel


@BindingAdapter("favoriteToDetailListener")
fun TextView.setFavoriteToDetailClickListener(article: Articles) {
    this.setOnClickListener { view ->
        val direction: NavDirections =
            FavoritesFragmentDirections.FavoritesToDetailFragment(article)
        Navigation.findNavController(view).navigate(direction)
    }
}

@BindingAdapter("favoriteFragmentModel", "favoriteViewModel")
fun ImageView.setFavoriteFragmentModel(
    article: Articles,
    viewModel: FavoritesViewModel
) {
    let { imageView ->
        imageView.setImageResource(R.drawable.ic_favorite_remove)
        imageView.setOnClickListener { viewModel.deleteArticle(article) }

        imageView.setOnLongClickListener {
            viewModel.deleteAllArticles()
            true
        }
    }

}