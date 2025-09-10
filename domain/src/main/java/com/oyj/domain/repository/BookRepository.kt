package com.oyj.domain.repository

import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBookList(query: String, sort: String): Flow<Result<List<Book>>>

    suspend fun getBookmarkList(): Flow<Result<List<Book>>>

    suspend fun inserteBookmark(book: Book)

    suspend fun deleteBookmark(isbn: String)

    suspend fun checkBookmark(isbn: String): Boolean
}