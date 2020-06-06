package com.developersbreach.developersbreach.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.developersbreach.model.Articles
import com.developersbreach.developersbreach.model.Tags
import com.developersbreach.developersbreach.utils.*

@Entity(tableName = DATABASE_ENTITY_TABLE_FAVORITES)
data class FavoritesEntity constructor(

    @ColumnInfo(name = COLUMN_FAVORITE_BASE_ID)
    val id: Int,

    @PrimaryKey
    @ColumnInfo(name = COLUMN_FAVORITE_ID)
    val articleId: Int,

    @ColumnInfo(name = COLUMN_FAVORITE_TITLE)
    val title: String,

    @ColumnInfo(name = COLUMN_FAVORITE_BANNER)
    val banner: String,

    @ColumnInfo(name = COLUMN_FAVORITE_POSTED_DATE)
    val postedDate: String,

    @ColumnInfo(name = COLUMN_FAVORITE_URL_LINK)
    val urlLink: String,

    @ColumnInfo(name = COLUMN_FAVORITE_EXCERPT)
    val excerpt: String,

    @ColumnInfo(name = COLUMN_FAVORITE_AUTHOR_ID)
    val authorId: Int,

    @ColumnInfo(name = COLUMN_FAVORITE_TAGS_LIST)
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