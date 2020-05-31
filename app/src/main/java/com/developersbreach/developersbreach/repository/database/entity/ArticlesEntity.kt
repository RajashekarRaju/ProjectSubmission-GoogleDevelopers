package com.developersbreach.developersbreach.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.developersbreach.model.Articles

@Entity(tableName = "ARTICLES_TABLE")
data class ArticlesEntity constructor(

    @ColumnInfo(name = "ID")
    val id: Int,

    @PrimaryKey
    @ColumnInfo(name = "ARTICLE_ID")
    val articleId: Int,

    @ColumnInfo(name = "ARTICLE_TITLE")
    val title: String
)

fun List<ArticlesEntity>.asDomainModel(): List<Articles> {
    return map {
        Articles(
            id = it.id,
            articleId = it.articleId,
            title = it.title
        )
    }
}
