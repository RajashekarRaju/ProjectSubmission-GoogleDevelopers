package com.developersbreach.developersbreach.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.repository.database.ArticlesDatabase
import com.developersbreach.developersbreach.repository.database.entity.asDatabaseModel
import com.developersbreach.developersbreach.repository.database.entity.asDomainModel
import com.developersbreach.developersbreach.repository.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


class ArticlesRepository(private val database: ArticlesDatabase) {

    val articles: LiveData<List<Articles>> =
        Transformations.map(database.articlesDao.getArticles()) { articleEntityList ->
            articleEntityList.asDomainModel()
        }

    val favorites: LiveData<List<Articles>> =
        Transformations.map(database.favoritesDao.getFavorites()) { favoritesEntityList ->
            favoritesEntityList.asDomainModel()
        }

    suspend fun refreshArticles() {
        withContext(Dispatchers.IO) {
            try {
                val articlesList: NetworkArticlesContainer = getArticles()
                database.articlesDao.insertAllArticles(articlesList.asDatabaseModel())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    suspend fun insertArticle(article: Articles) {
        withContext(Dispatchers.IO) {
            database.favoritesDao.insertFavorite(article.asDatabaseModel())
        }
    }

    suspend fun deleteArticle(article: Articles) {
        withContext(Dispatchers.IO) {
            database.favoritesDao.deleteFavorite(article.asDatabaseModel())
        }
    }

    suspend fun deleteAllArticles() {
        withContext(Dispatchers.IO) {
            database.favoritesDao.deleteAllFavorites()
        }
    }
}
