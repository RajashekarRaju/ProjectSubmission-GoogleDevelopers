package com.developersbreach.developersbreach.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developersbreach.developersbreach.repository.database.entity.ArticlesEntity

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM ARTICLES_TABLE")
    fun getArticles(): LiveData<List<ArticlesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticles(articles: List<ArticlesEntity>)

    @Query("SELECT * FROM ARTICLES_TABLE")
    fun getSearchableArticles() : List<ArticlesEntity>
}