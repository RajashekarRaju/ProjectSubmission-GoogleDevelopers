package com.developersbreach.developersbreach.repository.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.developersbreach.model.Articles


@Entity(tableName = "FAVORITES_TABLE")
data class FavoritesEntity constructor(

    @ColumnInfo(name = "ID")
    val id: Int,

    @PrimaryKey
    @ColumnInfo(name = "FAVORITE_ID")
    val articleId: Int,

    @ColumnInfo(name = "FAVORITE_TITLE")
    val title: String
)

fun List<FavoritesEntity>.asDomainModel(): List<Articles> {
    return map { favoritesEntity ->
        Articles(
            id = favoritesEntity.id,
            articleId = favoritesEntity.articleId,
            title = favoritesEntity.title
        )
    }
}

fun Articles.asDatabaseModel(): FavoritesEntity {
    return FavoritesEntity(
        id = this.id,
        articleId = this.articleId,
        title = this.title
    )
}