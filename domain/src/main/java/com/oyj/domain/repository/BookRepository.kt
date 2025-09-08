package com.oyj.domain.repository

import com.oyj.domain.entity.BookEntity
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBookList(query: String, sort: String): Flow<List<BookEntity>>

    suspend fun getFavoriteBookList(): Flow<List<BookEntity>>

    suspend fun insertFavoriteBook(book: BookEntity)

    suspend fun deleteFavoriteBook(isbn: String)

    suspend fun checkFavoriteBook(isbn: String): Boolean
}