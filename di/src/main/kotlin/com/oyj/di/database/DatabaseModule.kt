package com.oyj.di.database

import android.content.Context
import androidx.room.Room
import com.oyj.data.database.BookDatabase
import com.oyj.data.database.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideBookmarkDatabase(
        @ApplicationContext context: Context,
    ): BookDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = BookDatabase::class.java,
            name = "bookmark.db",
        ).build()
    }

    @Singleton
    @Provides
    fun provideBookmarkDao(database: BookDatabase): BookmarkDao {
        return database.bookmarkDao()
    }
}