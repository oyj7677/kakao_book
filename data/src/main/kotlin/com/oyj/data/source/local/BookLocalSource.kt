package com.oyj.data.source.local

import androidx.paging.PagingSource
import com.oyj.data.database.entity.BookmarkEntity

interface BookLocalSource {
    fun getBookmarkList(query: String, sort: String): PagingSource<Int, BookmarkEntity>
    suspend fun insertBookmark(bookmarkEntity: BookmarkEntity)
    suspend fun deleteBookmark(isbn: String)
    suspend fun checkBookmark(isbn: String): Boolean
    suspend fun getAllBookmarkIsbn(): List<String>

}