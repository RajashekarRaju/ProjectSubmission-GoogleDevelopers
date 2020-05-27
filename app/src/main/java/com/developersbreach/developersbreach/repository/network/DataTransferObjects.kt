package com.developersbreach.developersbreach.repository.network

import com.developersbreach.developersbreach.repository.database.ArticlesEntity


data class NetworkArticlesContainer(val articlesNetworks: List<ArticlesNetwork>)

data class ArticlesNetwork(
    val id: Int,
    val title: String
)

fun NetworkArticlesContainer.asDatabaseModel(): List<ArticlesEntity> {
    return articlesNetworks.map {
        ArticlesEntity(
            id = it.id,
            title = it.title
        )
    }
}