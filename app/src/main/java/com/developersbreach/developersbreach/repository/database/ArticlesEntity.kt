package com.developersbreach.developersbreach.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.developersbreach.model.Articles


@Entity(tableName = "ARTICLES_TABLE")
data class ArticlesEntity constructor(
    @PrimaryKey
    val id: Int,
    val title: String
)

fun List<ArticlesEntity>.asDomainModel(): List<Articles> {
    return map {
        Articles(
            id = it.id,
            title = it.title
        )
    }
}
