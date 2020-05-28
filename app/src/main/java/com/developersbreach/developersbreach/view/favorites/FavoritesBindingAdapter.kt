package com.developersbreach.developersbreach.view.favorites

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.viewModel.FavoritesViewModel


@BindingAdapter("removeFavorite", "getFavViewModel")
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