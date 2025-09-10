package com.oyj.domain.repository

import com.oyj.domain.entity.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBookList(query: String, sort: String): Flow<List<Book>>

    suspend fun getFavoriteBookList(): Flow<List<Book>>

    suspend fun insertFavoriteBook(book: Book)

    suspend fun deleteFavoriteBook(isbn: String)

    suspend fun checkFavoriteBook(isbn: String): Boolean
}