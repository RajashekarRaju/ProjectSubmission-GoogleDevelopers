package com.developersbreach.developersbreach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.developersbreach.developersbreach.repository.ArticlesRepository
import com.developersbreach.developersbreach.repository.database.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ArticlesRepository(getDatabase(application))
    private var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    fun checkFavoritesBeforeDelete(): Boolean {
        var isFavoriteEmpty = false
        viewModelScope.launch {
            isFavoriteEmpty = repository.isFavoriteItemsAvailable()
        }
        Timber.e(isFavoriteEmpty.toString())
        return isFavoriteEmpty
    }

    fun deleteAllArticles() {
        viewModelScope.launch {
            repository.deleteAllArticles()
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            repository.refreshArticles()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}