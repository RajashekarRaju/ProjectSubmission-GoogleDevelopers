package com.developersbreach.developersbreach.view.webView

import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


@BindingAdapter("bindShareClickListener")
fun FloatingActionButton.setFabShareClickListener(urlLink: String) {
    this.setOnClickListener {
        // Create new sharable intent.
        val sharingIntent = Intent(Intent.ACTION_SEND)
        // Set type
        sharingIntent.type = "text/plain"
        // Format the data which we send to other apps.
        val body: String = urlLink
        sharingIntent.putExtra(Intent.EXTRA_TEXT, body)
        // Start the intent.
        this.context.startActivity(sharingIntent)
    }
}


@BindingAdapter("bindWebViewClient")
fun WebView.setWebViewClient(progressBar: ProgressBar) {
    this.webViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }
    }
}
