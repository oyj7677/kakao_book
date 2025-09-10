package com.oyj.data.impl

import com.oyj.data.source.local.BookLocalSource
import com.oyj.data.source.remote.BookRemoteSource
import com.oyj.domain.entity.BookEntity
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookRemoteSource: BookRemoteSource,
    private val bookLocalSource: BookLocalSource
): BookRepository {
    override suspend fun getBookList(
        query: String,
        sort: String
    ): Flow<List<BookEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteBookList(): Flow<List<BookEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavoriteBook(book: BookEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavoriteBook(isbn: String) {
        TODO("Not yet implemented")
    }

    override suspend fun checkFavoriteBook(isbn: String): Boolean {
        TODO("Not yet implemented")
    }
}