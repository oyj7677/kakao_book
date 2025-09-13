package com.oyj.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oyj.data.database.entity.BookmarkEntity

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmark WHERE isbn = :isbn")
    suspend fun deleteBookmark(isbn: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmark WHERE isbn = :isbn)")
    suspend fun isBookmarkExists(isbn: String): Boolean

    @Query("SELECT * FROM bookmark")
    suspend fun getAllBookmark(): List<BookmarkEntity>

    @Query("SELECT isbn FROM bookmark")
    suspend fun getAllBookmarkIsbn(): List<String>

}