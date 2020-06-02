package com.developersbreach.developersbreach.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.model.Tags

@Entity(tableName = "ARTICLES_TABLE")
data class ArticlesEntity constructor(

    @ColumnInfo(name = "ID")
    val id: Int,

    @PrimaryKey
    @ColumnInfo(name = "ARTICLE_ID")
    val articleId: Int,

    @ColumnInfo(name = "ARTICLE_TITLE")
    val title: String,

    @ColumnInfo(name = "ARTICLE_BANNER")
    val banner: String,

    @ColumnInfo(name = "ARTICLE_POSTED_DATE")
    val postedDate: String,

    @ColumnInfo(name = "ARTICLE_URL_LINK")
    val urlLink: String,

    @ColumnInfo(name = "ARTICLE_EXCERPT")
    val excerpt: String,

    @ColumnInfo(name = "ARTICLE_AUTHOR_ID")
    val authorId: Int,

    @ColumnInfo(name = "ARTICLE_TAG_LIST")
    val tagList: List<Tags>
)

fun List<ArticlesEntity>.asDomainModel(): List<Articles> {
    return map {
        Articles(
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
