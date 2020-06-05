package com.developersbreach.developersbreach.view.search

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.developersbreach.developersbreach.R
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.utils.isNetworkConnected
import com.developersbreach.developersbreach.utils.showSnackBar


@BindingAdapter("bindSearchToDetailListener", "bindSearchFragment")
fun TextView.bindSearchArticleToDetailClickListener(
    article: Articles,
    fragment: SearchFragment
) {
    this.setOnClickListener { view: View? ->

        if (isNetworkConnected(this.context)) {
            val direction: NavDirections =
                SearchFragmentDirections.SearchToDetailFragment(article, false)
            Navigation.findNavController(view!!).navigate(direction)
        } else {
            showSnackBar(
                this.context.getString(R.string.no_internet_connection),
                fragment.requireActivity()
            )
        }
    }
}