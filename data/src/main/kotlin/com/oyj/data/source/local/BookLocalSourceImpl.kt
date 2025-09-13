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

    override suspend fun insertBookmark(bookmarkEntity: BookmarkEntity) {
        return bookmarkDao.insertBookmark(bookmarkEntity)
    }

    override suspend fun deleteBookmark(isbn: String) {
        return bookmarkDao.deleteBookmark(isbn)
    }

    override suspend fun checkBookmark(isbn: String): Boolean {
        return bookmarkDao.isBookmarkExists(isbn)
    }

    override suspend fun getAllBookmarkIsbn() : List<String> {
        return bookmarkDao.getAllBookmarkIsbn()
    }
}