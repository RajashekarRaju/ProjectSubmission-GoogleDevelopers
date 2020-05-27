package com.developersbreach.developersbreach.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.developersbreach.developersbreach.repository.database.dao.ArticlesDao
import com.developersbreach.developersbreach.repository.database.dao.FavoritesDao
import com.developersbreach.developersbreach.repository.database.entity.ArticlesEntity
import com.developersbreach.developersbreach.repository.database.entity.FavoritesEntity


@Database(entities = [ArticlesEntity::class, FavoritesEntity::class], version = 1, exportSchema = false)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract val articlesDao: ArticlesDao
    abstract val favoritesDao: FavoritesDao
}

private lateinit var INSTANCE: ArticlesDatabase
private const val DATABASE_NAME: String = "ARTICLES_DATABASE"

fun getDatabase(context: Context): ArticlesDatabase {
    synchronized(ArticlesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ArticlesDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}
