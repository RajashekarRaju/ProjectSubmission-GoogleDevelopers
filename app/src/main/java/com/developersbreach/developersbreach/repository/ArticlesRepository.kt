package com.developersbreach.developersbreach.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.repository.database.ArticlesDatabase
import com.developersbreach.developersbreach.repository.database.ArticlesEntity
import com.developersbreach.developersbreach.repository.database.asDomainModel
import com.developersbreach.developersbreach.repository.network.fetchArticleJsonData
import com.developersbreach.developersbreach.repository.network.startResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


class ArticlesRepository(private val database: ArticlesDatabase) {

    val articles: LiveData<List<Articles>> =
        Transformations.map(database.articlesDao.getArticles()) { articleEntityList ->
            articleEntityList.asDomainModel()
        }

    suspend fun refreshArticles() {
        withContext(Dispatchers.IO) {
            try {
                val responseString: String = startResponse()
                val articlesEntityList: List<ArticlesEntity> = fetchArticleJsonData(responseString)
                database.articlesDao.insertAllArticles(articlesEntityList)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
