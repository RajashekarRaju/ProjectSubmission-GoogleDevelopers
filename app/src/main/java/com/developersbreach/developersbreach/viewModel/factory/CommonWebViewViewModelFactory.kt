package com.developersbreach.developersbreach.viewModel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.developersbreach.viewModel.CommonWebViewViewModel

class CommonWebViewViewModelFactory(
    private val application: Application,
    private val urlString: String
) :
    ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommonWebViewViewModel::class.java)) {
            return CommonWebViewViewModel(application, urlString) as T
        }
        throw IllegalArgumentException("Cannot create instance for class CommonWebViewViewModel")
    }
}