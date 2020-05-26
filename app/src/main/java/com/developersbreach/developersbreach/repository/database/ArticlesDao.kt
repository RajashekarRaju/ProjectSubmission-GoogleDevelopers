package com.developersbreach.developersbreach.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticlesDao {

    @Query("select * from ARTICLES_TABLE")
    fun getArticles(): LiveData<List<ArticlesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticles(articles: List<ArticlesEntity>)
}