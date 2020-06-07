package com.developersbreach.developersbreach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.repository.ArticlesRepository
import com.developersbreach.developersbreach.repository.database.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class DetailViewModel(
    application: Application,
    article: Articles,
    userInputEnabled: Boolean
) :
    AndroidViewModel(application) {

    private val repository = ArticlesRepository(getDatabase(application))
    private var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _viewPagerList: MutableLiveData<List<Articles>> = MutableLiveData()
    val viewPagerList: LiveData<List<Articles>>
        get() = _viewPagerList

    private val _selectedArticle = MutableLiveData<Articles>()
    val selectedArticle: LiveData<Articles>
        get() = _selectedArticle

    private val _userInputEnabled = MutableLiveData<Boolean>()
    val userInputEnabled: LiveData<Boolean>
        get() = _userInputEnabled

    init {
        viewModelScope.launch {
            _viewPagerList.postValue(repository.searchableArticle())
            _selectedArticle.postValue(article)
            _userInputEnabled.postValue(userInputEnabled)
        }
    }

    fun insertFavorite(article: Articles) {
        viewModelScope.launch {
            repository.insertArticle(article)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}