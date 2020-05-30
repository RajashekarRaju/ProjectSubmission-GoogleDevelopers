package com.developersbreach.developersbreach.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.developersbreach.developersbreach.repository.database.entity.FavoritesEntity


@Dao
interface FavoritesDao {

    @Query("select * from FAVORITES_TABLE ORDER BY id DESC")
    fun getFavorites(): LiveData<List<FavoritesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoritesEntity)

    @Delete
    fun deleteFavorite(favorite: FavoritesEntity)

    @Query("delete from FAVORITES_TABLE")
    fun deleteAllFavorites()
}