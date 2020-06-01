package com.developersbreach.developersbreach.repository.network

import com.developersbreach.developersbreach.repository.database.entity.ArticlesEntity


data class NetworkArticlesContainer(val articlesNetworks: List<ArticlesNetwork>)

data class ArticlesNetwork(
    val id: Int,
    val articleId: Int,
    val title: String,
    val banner: String,
    val postedDate: String,
    val urlLink: String,
    val excerpt: String,
    val authorId: Int
)

fun NetworkArticlesContainer.asDatabaseModel(): List<ArticlesEntity> {
    return articlesNetworks.map {
        ArticlesEntity(
            id = it.id,
            articleId = it.articleId,
            title = it.title,
            banner = it.banner,
            postedDate = it.postedDate,
            urlLink = it.urlLink,
            excerpt = it.excerpt,
            authorId = it.authorId
        )
    }
}
