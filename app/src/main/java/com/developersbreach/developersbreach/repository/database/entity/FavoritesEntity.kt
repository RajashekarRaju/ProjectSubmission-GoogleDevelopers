package com.developersbreach.developersbreach.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.model.Tags


@Entity(tableName = "FAVORITES_TABLE")
data class FavoritesEntity constructor(

    @ColumnInfo(name = "ID")
    val id: Int,

    @PrimaryKey
    @ColumnInfo(name = "FAVORITE_ID")
    val articleId: Int,

    @ColumnInfo(name = "FAVORITE_TITLE")
    val title: String,

    @ColumnInfo(name = "FAVORITE_BANNER")
    val banner: String,

    @ColumnInfo(name = "FAVORITE_POSTED_DATE")
    val postedDate: String,

    @ColumnInfo(name = "FAVORITE_URL_LINK")
    val urlLink: String,

    @ColumnInfo(name = "FAVORITE_EXCERPT")
    val excerpt: String,

    @ColumnInfo(name = "FAVORITE_AUTHOR_ID")
    val authorId: Int,

    @ColumnInfo(name = "FAVORITE_TAG_LIST")
    val tagList: List<Tags>
)

fun List<FavoritesEntity>.asDomainModel(): List<Articles> {
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

fun Articles.asDatabaseModel(): FavoritesEntity {
    return FavoritesEntity(
        id = this.id,
        articleId = this.articleId,
        title = this.title,
        banner = this.banner,
        postedDate = this.postedDate,
        urlLink = this.urlLink,
        excerpt = this.excerpt,
        authorId = this.authorId,
        tagList = this.tagList
    )
}