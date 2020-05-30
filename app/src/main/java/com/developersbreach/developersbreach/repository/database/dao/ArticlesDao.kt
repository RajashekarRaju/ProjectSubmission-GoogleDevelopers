package com.developersbreach.developersbreach.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developersbreach.developersbreach.repository.database.entity.ArticlesEntity

@Dao
interface ArticlesDao {

    @Query("select * from ARTICLES_TABLE ORDER BY id DESC")
    fun getArticles(): LiveData<List<ArticlesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticles(articles: List<ArticlesEntity>)

    @Query("select * from ARTICLES_TABLE ORDER BY id DESC")
    fun getSearchableArticles() : List<ArticlesEntity>
}