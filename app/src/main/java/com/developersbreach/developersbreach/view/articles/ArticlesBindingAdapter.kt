package com.developersbreach.developersbreach.view.articles

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.utils.capitalizeWord
import com.developersbreach.developersbreach.utils.isNetworkConnected
import com.developersbreach.developersbreach.utils.showSnackBar
import com.developersbreach.developersbreach.viewModel.ArticlesViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar


@BindingAdapter("bindArticleToDetailListener", "bindArticleFragment")
fun MaterialCardView.setArticleToDetailClickListener(
    article: Articles,
    fragment: ArticlesFragment
) {
    this.setOnClickListener { view: View ->
        if (isNetworkConnected(this.context)) {
            val direction: NavDirections =
                ArticlesFragmentDirections.ArticlesToDetailFragment(article, true)
            Navigation.findNavController(view).navigate(direction)
        } else {
            showSnackBar(
                this.context.getString(R.string.no_internet_connection),
                fragment.requireActivity()
            )
        }
    }
}


@BindingAdapter("bindArticleItemBanner")
fun ImageView.setArticleItemBanner(
    imageUrl: String
) {
    Glide.with(this.context).load(imageUrl).into(this)
}


@BindingAdapter("bindArticleItemTitle")
fun TextView.setArticleItemTitle(
    title: String
) {
    this.text = capitalizeWord(title)
}


@BindingAdapter("bindArticleFragmentModel", "bindArticleViewModel")
fun ImageView.setAddArticleToFavoritesListener(
    article: Articles,
    viewModel: ArticlesViewModel
) {
    this.setImageResource(R.drawable.ic_favorite_add)
    this.setOnClickListener { view ->
        viewModel.insertFavorite(article)
        Snackbar.make(
            view,
            this.context.getString(R.string.snackbar_added_to_favorites_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}

