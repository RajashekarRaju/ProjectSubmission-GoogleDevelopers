package com.developersbreach.developersbreach.view.detail

import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.developersbreach.developersbreach.model.Articles
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout


@BindingAdapter("bindImage")
fun ImageView.setImageResource(articles: Articles) {
    Glide.with(this.context).load(articles.banner).into(this)
}


@BindingAdapter("bindToolbar")
fun Toolbar.setToolbar(articles: Articles) {
    let { toolBar ->
        toolBar.title = articles.title
        toolBar.setNavigationOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }
    }
}


@BindingAdapter("bindAppbar", "collapsingToolbarView")
fun AppBarLayout.setAppbar(articles: Articles, collapsingToolbar: CollapsingToolbarLayout) {
    this.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
        var isShow = false
        var scrollRange = -1
        override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                // Show title when completely collapsed.
                collapsingToolbar.title = articles.title
                isShow = true
            } else if (isShow) {
                // Hide title when collapsedToolBar is completely visible using empty string.
                collapsingToolbar.title = ""
                isShow = false
            }
        }
    })
}
