package com.oyj.data.source.local

import androidx.paging.PagingSource
import com.oyj.data.database.BookmarkDao
import com.oyj.data.database.entity.BookmarkEntity
import javax.inject.Inject

class BookLocalSourceImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookLocalSource {
    override fun getBookmarkList(
        query: String,
        sort: String
    ): PagingSource<Int, BookmarkEntity> {
        return bookmarkDao.getAllBookmark(query = query, sort = sort)
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