package com.oyj.domain.repository

import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBookList(query: String, sort: String): Flow<Result<List<Book>>>

    suspend fun getFavoriteBookList(): Flow<Result<List<Book>>>

    suspend fun insertFavoriteBook(book: Book)

    suspend fun deleteFavoriteBook(isbn: String)

    suspend fun checkFavoriteBook(isbn: String): Boolean
}