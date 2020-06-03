package com.developersbreach.developersbreach.repository.network

import com.developersbreach.developersbreach.model.Author
import com.developersbreach.developersbreach.model.Tags
import com.developersbreach.developersbreach.repository.database.entity.ArticlesEntity


data class NetworkArticlesContainer(val articlesNetworks: List<ArticlesNetwork>)
data class NetworkAuthorContainer(val authorNetwork: AuthorNetwork)

data class ArticlesNetwork(
    val id: Int,
    val articleId: Int,
    val title: String,
    val banner: String,
    val postedDate: String,
    val urlLink: String,
    val excerpt: String,
    val authorId: Int,
    val tagList: List<Tags>
)

data class AuthorNetwork(
    val authorId: Int,
    val name: String,
    val description: String,
    val avatar: String
)

fun NetworkAuthorContainer.asDomainModel(): Author {
    return authorNetwork.let {
        Author(
            authorId = it.authorId,
            name = it.name,
            description = it.description,
            avatar = it.avatar
        )
    }
}

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
            authorId = it.authorId,
            tagList = it.tagList
        )
    }
}
