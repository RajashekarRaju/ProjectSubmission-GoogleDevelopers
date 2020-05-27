package com.developersbreach.developersbreach.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.developersbreach.model.Articles


@Entity(tableName = "FAVORITES_TABLE")
data class FavoritesEntity constructor(
    @PrimaryKey
    val id: Int,
    val title: String
)

fun List<FavoritesEntity>.asDomainModel(): List<Articles> {
    return map { favoritesEntity ->
        Articles(
            id = favoritesEntity.id,
            title = favoritesEntity.title
        )
    }
}

fun Articles.asDatabaseModel(): FavoritesEntity {
    return FavoritesEntity(
        id = this.id,
        title = this.title
    )
}