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

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ArticlesRepository(getDatabase(application))
    private var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val favorites: LiveData<List<Articles>> = repository.favorites

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun deleteArticle(article: Articles) {
        CoroutineScope(viewModelJob + Dispatchers.IO).launch {
            repository.deleteArticle(article)
        }
    }

    fun deleteAllArticles() {
        CoroutineScope(viewModelJob + Dispatchers.IO).launch {
            repository.deleteAllArticles()
        }
    }
}