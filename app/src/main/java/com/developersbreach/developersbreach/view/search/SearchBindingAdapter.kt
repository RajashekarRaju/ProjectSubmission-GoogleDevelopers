package com.developersbreach.developersbreach.view.search

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.developersbreach.developersbreach.model.Articles


@BindingAdapter("searchToDetailListener")
fun bindSearchArticleToDetailClickListener(
    textView: TextView,
    article: Articles
) {
    textView.setOnClickListener { view: View? ->
        val direction: NavDirections =
            SearchFragmentDirections.SearchToDetailFragment(article, false)
        Navigation.findNavController(view!!).navigate(direction)
    }
}