package com.oyj.data.source.local

import com.oyj.data.database.entity.BookmarkEntity

interface BookLocalSource {
    suspend fun getBookmarkList(): List<BookmarkEntity>
}