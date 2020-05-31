package com.developersbreach.developersbreach.viewModel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.viewModel.DetailViewModel

class DetailViewModelFactory(
    private val application: Application,
    private val article: Articles,
    private val userInputEnabled: Boolean
) :
    ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(application, article, userInputEnabled) as T
        }
        throw IllegalArgumentException("Cannot create instance for class DetailViewModel")
    }
}
