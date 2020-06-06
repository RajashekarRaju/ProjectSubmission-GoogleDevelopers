package com.developersbreach.developersbreach.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.developersbreach.developersbreach.repository.database.entity.FavoritesEntity


@Dao
interface FavoritesDao {

    @Query("SELECT * FROM FAVORITES_TABLE")
    fun getFavorites(): LiveData<List<FavoritesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoritesEntity)

    @Delete
    fun deleteFavorite(favorite: FavoritesEntity)

    @Query("DELETE FROM FAVORITES_TABLE")
    fun deleteAllFavorites()
}