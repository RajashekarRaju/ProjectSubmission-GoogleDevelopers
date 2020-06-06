package com.developersbreach.developersbreach.view.detail

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.viewModel.DetailViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


@BindingAdapter("bindCollapsingBannerImage")
fun ImageView.setImageResource(articles: Articles) {
    Glide.with(this.context).load(articles.banner).into(this)
}


@BindingAdapter("bindTagsText")
fun TextView.setTagText(articles: Articles) {
    val tagList = articles.tagList
    for (tag in tagList) {
        this.text = tagList.toString()
    }
}


@BindingAdapter("bindButtonWebView")
fun Button.setButtonWebView(articles: Articles) {
    this.text = this.context.getString(R.string.open_article_button_text)
    this.setOnClickListener {
        val action: NavDirections =
            DetailFragmentDirections.DetailToWebFragment(articles.urlLink)
        findNavController().navigate(action)
    }
}


@BindingAdapter("bindAppbar", "bindCollapsingToolbarView")
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


@BindingAdapter("bindDetailFab", "bindDetailViewModel")
fun FloatingActionButton.setDetailFab(article: Articles, viewModel: DetailViewModel) {
    this.setOnClickListener { view ->
        viewModel.insertFavorite(article)
        Snackbar.make(
            view,
            this.context.getString(R.string.snackbar_added_to_favorites_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}


@BindingAdapter("bindDetailToolbar", "bindViewPager", "bindViewPagerFab")
fun Toolbar.setToolbar(
    articles: Articles,
    viewPager: ViewPager2,
    viewPagerFab: FloatingActionButton
) {
    let { toolBar ->

        toolBar.title = articles.title
        toolBar.setNavigationOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        toolBar.navigationIcon = null
                        viewPagerFab.visibility = View.INVISIBLE
                    }
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        toolBar.setNavigationIcon(R.drawable.ic_up_button)
                        viewPagerFab.visibility = View.VISIBLE
                    }
                    ViewPager2.SCROLL_STATE_SETTLING -> {
                        toolBar.navigationIcon = null
                        viewPagerFab.visibility = View.INVISIBLE
                    }
                }
            }
        })
    }
}
