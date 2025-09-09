package com.oyj.di.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oyj.data.database.entity.BookmarkEntity

@Database(
    version = 1,
    entities = [
        BookmarkEntity::class,
    ],
    exportSchema = true,
)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}