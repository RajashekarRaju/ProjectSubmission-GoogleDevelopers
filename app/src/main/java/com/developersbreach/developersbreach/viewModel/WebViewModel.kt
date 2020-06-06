package com.developersbreach.developersbreach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WebViewModel(
    application: Application,
    urlLink: String
) : AndroidViewModel(application) {

    private val _articleLink = MutableLiveData<String>()
    val articleLink: LiveData<String>
        get() = _articleLink

    init {
        _articleLink.value = urlLink
    }
}
