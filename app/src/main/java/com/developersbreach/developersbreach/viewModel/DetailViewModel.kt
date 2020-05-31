package com.developersbreach.developersbreach.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.repository.ArticlesRepository
import com.developersbreach.developersbreach.repository.database.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class DetailViewModel(application: Application, article: Articles) :
    AndroidViewModel(application) {

    private val repository = ArticlesRepository(getDatabase(application))
    private var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _articleList: MutableLiveData<List<Articles>> = MutableLiveData()
    val articleList: MutableLiveData<List<Articles>>
        get() = _articleList

    private val _selectedArticle = MutableLiveData<Articles>()
    val selectedArticle: MutableLiveData<Articles>
        get() = _selectedArticle

    init {
        _selectedArticle.value = article

        viewModelScope.launch {
            _articleList.postValue(repository.searchableArticle())
        }
    }
}