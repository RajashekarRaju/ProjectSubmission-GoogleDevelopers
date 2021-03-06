package com.developersbreach.developersbreach.repository.database

import android.content.Context
import androidx.room.*
import com.developersbreach.developersbreach.repository.database.dao.ArticlesDao
import com.developersbreach.developersbreach.repository.database.dao.FavoritesDao
import com.developersbreach.developersbreach.repository.database.entity.ArticlesEntity
import com.developersbreach.developersbreach.repository.database.entity.FavoritesEntity
import com.developersbreach.developersbreach.utils.DATABASE_NAME


@Database(
    entities = [ArticlesEntity::class, FavoritesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract val articlesDao: ArticlesDao
    abstract val favoritesDao: FavoritesDao
}

private lateinit var INSTANCE: ArticlesDatabase

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
