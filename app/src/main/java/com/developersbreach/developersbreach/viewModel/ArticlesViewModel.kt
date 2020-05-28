package com.developersbreach.developersbreach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.repository.ArticlesRepository
import com.developersbreach.developersbreach.repository.database.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class ArticlesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ArticlesRepository(getDatabase(application))
    private var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val articles: LiveData<List<Articles>> = repository.articles
    val favArticles: LiveData<List<Articles>> = repository.favorites

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            repository.refreshArticles()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun insertFavorite(article: Articles) {
        viewModelScope.launch {
            repository.insertArticle(article)
        }
    }

    fun deleteArticle(article: Articles) {
        CoroutineScope(viewModelJob + Dispatchers.IO).launch {
            repository.deleteArticle(article)
        }
    }
}