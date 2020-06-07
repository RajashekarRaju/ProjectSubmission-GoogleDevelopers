package com.developersbreach.developersbreach.view.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
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
import com.developersbreach.developersbreach.model.Tags
import com.developersbreach.developersbreach.viewModel.DetailViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


@BindingAdapter("bindCollapsingBannerImage")
fun ImageView.setImageResource(
    urlString: String
) {
    Glide.with(this.context).load(urlString).into(this)
}


@BindingAdapter("bindButtonWebView")
fun Button.setButtonWebView(
    stringUrl: String
) {
    this.text = this.context.getString(R.string.open_article_button_text)
    this.setOnClickListener {
        val action: NavDirections =
            DetailFragmentDirections.DetailToWebFragment(stringUrl)
        findNavController().navigate(action)
    }
}


@BindingAdapter("bindArticleExcerpt")
fun TextView.setArticleExcerpt(
    excerpt: String
) {
    val formatExcerpt: String = excerpt.drop(3).dropLast(5)
    this.text = formatExcerpt
}


@SuppressLint("SetTextI18n")
@BindingAdapter("bindAuthorAndData")
fun TextView.setAuthorAndDate(
    date: String
) {
    val formatDate: String = date.dropLast(9)
    val author: String = this.context.getString(R.string.author_name)
    this.text = "$author  |  $formatDate"
}


@BindingAdapter("bindChipGroup")
fun ChipGroup.setChipGroup(
    tagsList: List<Tags>
) {
    val inflater: LayoutInflater = LayoutInflater.from(this.context)
    val chipList: List<Chip> = tagsList.map { tags ->
        val chip = inflater.inflate(R.layout.item_action_chip, this, false) as Chip
        chip.text = tags.name
        chip
    }

    this.removeAllViews()
    for (currentChip in chipList) {
        this.addView(currentChip)
    }
}


@BindingAdapter("bindDetailFab", "bindDetailViewModel")
fun FloatingActionButton.setDetailFab(
    article: Articles,
    viewModel: DetailViewModel
) {
    this.setOnClickListener { view ->
        viewModel.insertFavorite(article)
        Snackbar.make(
            view,
            this.context.getString(R.string.snackbar_added_to_favorites_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}


@BindingAdapter("bindViewPager", "bindViewPagerFab")
fun Toolbar.setToolbar(
    viewPager: ViewPager2,
    viewPagerFab: FloatingActionButton
) {
    let { toolBar ->

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
