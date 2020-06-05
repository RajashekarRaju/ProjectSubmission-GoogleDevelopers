package com.developersbreach.developersbreach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.developersbreach.developersbreach.R


class CommonWebViewViewModel(application: Application, urlString: String) :
    AndroidViewModel(application) {

    private lateinit var _webUrl: String
    var webUrl: String
        get() = _webUrl
        set(value) {
            _webUrl = value
        }

    private lateinit var _toolBarTitle: String
    var toolBarTitle: String
        get() = _toolBarTitle
        set(value) {
            _toolBarTitle = value
        }

    init {
        if (urlString == "Contact") {
            _toolBarTitle = application.getString(R.string.web_view_toolbar_title_contact)
            _webUrl = application.getString(R.string.url_site_contact)
        } else if (urlString == "Developer") {
            _toolBarTitle = application.getString(R.string.web_view_toolbar_title_github)
            _webUrl = application.getString(R.string.url_site_developer)
        }
    }
}