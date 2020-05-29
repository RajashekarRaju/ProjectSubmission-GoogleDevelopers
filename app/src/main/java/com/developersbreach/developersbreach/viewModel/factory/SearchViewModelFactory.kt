package com.developersbreach.developersbreach.viewModel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.developersbreach.viewModel.SearchViewModel

class SearchViewModelFactory(val app: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(app) as T
        }
        throw IllegalArgumentException("Cannot create instance for class SearchViewModel")
    }
}
