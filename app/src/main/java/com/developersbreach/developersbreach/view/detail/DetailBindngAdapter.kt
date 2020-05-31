package com.developersbreach.developersbreach.view.detail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.developersbreach.developersbreach.model.Articles


@BindingAdapter("bindDetailText")
fun TextView.setDetailTextView(articles: Articles) {
    this.text = articles.title
}