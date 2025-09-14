package com.oyj.data.database

import androidx.paging.PagingSource
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

    @Query("""
        SELECT * FROM bookmark 
        WHERE (:query = '' OR title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%')
        ORDER BY 
        CASE WHEN :sort = 'Title_ASC' THEN title END ASC,
        CASE WHEN :sort = 'Title_DESC' THEN title END DESC,
        CASE WHEN :sort = 'Price_ASC' THEN price END ASC,
        CASE WHEN :sort = 'Price_DESC' THEN price END DESC
    """)
    fun getAllBookmark(query: String = "", sort: String = ""): PagingSource<Int, BookmarkEntity>

    @Query("SELECT isbn FROM bookmark")
    suspend fun getAllBookmarkIsbn(): List<String>

}