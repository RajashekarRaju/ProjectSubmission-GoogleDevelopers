package com.developersbreach.developersbreach.viewModel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.developersbreach.viewModel.WebViewModel

class WebViewModelFactory(
    private val application: Application,
    private val urlLink: String
) :
    ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WebViewModel::class.java)) {
            return WebViewModel(application, urlLink) as T
        }
        throw IllegalArgumentException("Cannot create instance for class WebViewModel")
    }
}