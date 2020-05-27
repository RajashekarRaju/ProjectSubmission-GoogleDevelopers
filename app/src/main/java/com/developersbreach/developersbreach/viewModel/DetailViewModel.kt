package com.developersbreach.developersbreach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developersbreach.developersbreach.model.Articles

class DetailViewModel(application: Application, article: Articles) :
    AndroidViewModel(application) {

    private val _selectedArticle = MutableLiveData<Articles>()
    val selectedArticle: LiveData<Articles>
        get() = _selectedArticle

    init {
        _selectedArticle.value = article
    }
}