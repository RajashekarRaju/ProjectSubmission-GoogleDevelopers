package com.developersbreach.developersbreach.view.favorites

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.utils.isNetworkConnected
import com.developersbreach.developersbreach.utils.showSnackBar
import com.developersbreach.developersbreach.viewModel.FavoritesViewModel


@BindingAdapter("bindFavoriteToDetailListener", "bindFavoritesFragment")
fun TextView.setFavoriteToDetailClickListener(
    article: Articles,
    fragment: FavoritesFragment
) {
    this.setOnClickListener { view ->

        if (isNetworkConnected(this.context)) {
            val direction: NavDirections =
                FavoritesFragmentDirections.FavoritesToDetailFragment(article, false)
            Navigation.findNavController(view).navigate(direction)
        } else {
            showSnackBar(
                this.context.getString(R.string.no_internet_connection),
                fragment.requireActivity()
            )
        }

    }
}

@BindingAdapter("bindFavoriteFragmentModel", "bindFavoriteViewModel")
fun ImageView.setFavoriteFragmentModel(
    article: Articles,
    viewModel: FavoritesViewModel
) {
    let { imageView ->
        imageView.setImageResource(R.drawable.ic_favorite_remove)
        imageView.setOnClickListener {
            viewModel.deleteArticle(article)
        }

        imageView.setOnLongClickListener {
            viewModel.deleteAllArticles()
            true
        }
    }

}