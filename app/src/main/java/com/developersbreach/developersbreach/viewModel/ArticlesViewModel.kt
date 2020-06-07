package com.developersbreach.developersbreach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.model.Author
import com.developersbreach.developersbreach.repository.ArticlesRepository
import com.developersbreach.developersbreach.repository.database.getDatabase
import com.developersbreach.developersbreach.utils.isNetworkConnected
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class ArticlesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ArticlesRepository(getDatabase(application))
    private var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _articles: LiveData<List<Articles>> = repository.articles
    val articles: LiveData<List<Articles>>
        get() = _articles

    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean>
        get() = _isInternetAvailable

    private val _author = MutableLiveData<Author>()
    val author: LiveData<Author>
        get() = _author

    init {
        refreshArticlesData()
        _isInternetAvailable.value = isNetworkConnected(application.applicationContext)
    }

    private fun refreshArticlesData() {
        viewModelScope.launch {
            repository.refreshArticles()
        }
    }

    fun insertFavorite(article: Articles) {
        viewModelScope.launch {
            repository.insertArticle(article)
        }
    }

    fun getAuthorDetails() {
        viewModelScope.launch {
            val refreshAuthor = repository.getAuthor()
            _author.postValue(refreshAuthor)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}