package com.developersbreach.developersbreach.repository.network

import com.developersbreach.developersbreach.repository.database.entity.ArticlesEntity


data class NetworkArticlesContainer(val articlesNetworks: List<ArticlesNetwork>)

data class ArticlesNetwork(
    val id: Int,
    val articleId: Int,
    val title: String
)

fun NetworkArticlesContainer.asDatabaseModel(): List<ArticlesEntity> {
    return articlesNetworks.map {
        ArticlesEntity(
            id = it.id,
            articleId = it.articleId,
            title = it.title
        )
    }
}
