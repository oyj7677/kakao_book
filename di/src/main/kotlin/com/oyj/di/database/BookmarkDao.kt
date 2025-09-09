package com.oyj.di.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oyj.domain.entity.BookEntity

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookEntity)

    @Query("DELETE FROM bookmark WHERE isbn = :isbn")
    suspend fun deleteBookmark(isbn: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmark WHERE isbn = :isbn)")
    suspend fun isBookmarkExists(isbn: String): Boolean

    @Query("SELECT * FROM bookmark")
    suspend fun getAllBookmark(): List<BookEntity>
}