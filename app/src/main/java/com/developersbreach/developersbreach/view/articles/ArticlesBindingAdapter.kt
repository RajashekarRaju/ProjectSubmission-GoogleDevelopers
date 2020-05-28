package com.developersbreach.developersbreach.view.articles

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.viewModel.ArticlesViewModel
import timber.log.Timber


@BindingAdapter("addFavorite", "getViewModel", "addReference")
fun bindArticleAddToFavoritesListener(
    imageView: ImageView,
    article: Articles,
    viewModel: ArticlesViewModel,
    fragment: ArticlesFragment
) {

    imageView.setOnClickListener {
        viewModel.insertFavorite(article)
        imageView.setImageResource(R.drawable.ic_favorite_remove)
    }

    viewModel.favArticles.observe(fragment, Observer { articles ->

        for (i: Int in articles.indices) {
            val id: Int = articles[i].id
            if (id != article.id) {
                imageView.setImageResource(R.drawable.ic_favorite_add)
                Timber.e(id.toString())
            } else {
                imageView.setImageResource(R.drawable.ic_favorite_remove)
            }
        }
    })
}
