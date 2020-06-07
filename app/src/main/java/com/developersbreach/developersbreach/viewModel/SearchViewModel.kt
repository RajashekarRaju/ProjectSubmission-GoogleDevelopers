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
import java.util.*
import kotlin.collections.ArrayList


class SearchViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = ArticlesRepository(getDatabase(application))

    private var viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _articles: LiveData<List<Articles>> = repository.articles
    val articles: LiveData<List<Articles>>
        get() = _articles

    private lateinit var searchableArticle: List<Articles>
    private val filteredList = MutableLiveData<List<Articles>>()

    init {
        viewModelScope.launch {
            searchableArticle = repository.searchableArticle()
        }
    }

    fun filter(query: String): LiveData<List<Articles>> {
        filterWithQuery(query)
        return filteredList
    }

    private fun filterWithQuery(query: String) {
        val filterList = ArrayList<Articles>()
        for (article: Articles in searchableArticle) {
            val formatTitle: String = article.title.toLowerCase(Locale.getDefault())
            if (formatTitle.contains(query)) {
                filterList.add(article)
            }
        }
        filteredList.value = filterList
    }
}