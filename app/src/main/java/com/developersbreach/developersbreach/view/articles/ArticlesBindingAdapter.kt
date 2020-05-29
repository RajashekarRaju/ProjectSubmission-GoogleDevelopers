package com.developersbreach.developersbreach.view.articles

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.viewModel.ArticlesViewModel


@BindingAdapter("articleToDetailListener")
fun bindArticleToDetailClickListener(
    textView: TextView,
    article: Articles
) {
    textView.setOnClickListener { view: View ->
        val direction: NavDirections = ArticlesFragmentDirections.ArticlesToDetailFragment(article)
        Navigation.findNavController(view).navigate(direction)
    }
}


@BindingAdapter("articleFragmentModel", "articleViewModel")
fun bindAddArticleToFavoritesListener(
    imageView: ImageView,
    article: Articles,
    viewModel: ArticlesViewModel
) {
    imageView.setOnClickListener {
        viewModel.insertFavorite(article)
    }
}

