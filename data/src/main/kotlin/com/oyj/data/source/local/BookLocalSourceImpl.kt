package com.oyj.data.source.local

import com.oyj.data.database.BookmarkDao
import com.oyj.data.database.entity.BookmarkEntity
import javax.inject.Inject

class BookLocalSourceImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookLocalSource {
    override suspend fun getBookmarkList(): List<BookmarkEntity> {
        return bookmarkDao.getAllBookmark()
    }
}