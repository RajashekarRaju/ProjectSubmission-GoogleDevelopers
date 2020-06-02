package com.developersbreach.developersbreach.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.model.Author
import com.developersbreach.developersbreach.model.Tags
import com.developersbreach.developersbreach.repository.database.ArticlesDatabase
import com.developersbreach.developersbreach.repository.database.entity.ArticlesEntity
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

    suspend fun searchableArticle(): List<Articles> {
        var searchableArticles = ArrayList<Articles>()
        withContext(Dispatchers.IO) {
            val articleEntity: List<ArticlesEntity> = database.articlesDao.getSearchableArticles()
            searchableArticles = articleEntity.asDomainModel() as ArrayList<Articles>
        }
        return searchableArticles
    }

    suspend fun getAuthorDetails() {
        withContext(Dispatchers.IO) {
            try {
                val author: Author = getAuthor()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getArticleTags(articleId: Int): List<Tags> {
        var tags: List<Tags> = ArrayList()
        withContext(Dispatchers.IO) {
            try {
                tags = getTags(articleId)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return tags
    }
}
