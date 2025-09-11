package com.oyj.data.source.local

import com.oyj.data.database.entity.BookmarkEntity

interface BookLocalSource {
    suspend fun getBookmarkList(): List<BookmarkEntity>
    suspend fun insertBookmark(bookmarkEntity: BookmarkEntity)
    suspend fun deleteBookmark(isbn: String)
    suspend fun checkBookmark(isbn: String): Boolean
}