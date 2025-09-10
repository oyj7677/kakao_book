package com.oyj.data.impl

import android.util.Log
import com.oyj.data.mapper.Mapper.toData
import com.oyj.data.mapper.Mapper.toDomainList
import com.oyj.data.source.local.BookLocalSource
import com.oyj.data.source.remote.BookRemoteSource
import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookRemoteSource: BookRemoteSource,
    private val bookLocalSource: BookLocalSource
): BookRepository {
    override suspend fun getBookList(
        query: String,
        sort: String
    ): Flow<Result<List<Book>>> {
        Log.d(TAG, "getBookList: query = $query, sort = $sort")
        return flow {
            runCatching {
                val bookList = bookRemoteSource.getBookList(query).toDomainList()
                emit(Result.Success(bookList))
            }.onFailure {
                Log.e(TAG, "getBookList: ${it.message}")
                emit(Result.Error(it))
            }
        }
    }

    override suspend fun getFavoriteBookList(): Flow<Result<List<Book>>> {
        return flow {
            runCatching {
                val bookList = bookLocalSource.getBookmarkList().toDomainList()
                emit(Result.Success(bookList))
            }.onFailure{
                Log.e(TAG, "getFavoriteBookList: ${it.message}")
                emit(Result.Error(it))
            }
        }
    }

    override suspend fun insertFavoriteBook(book: Book) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavoriteBook(isbn: String) {
        TODO("Not yet implemented")
    }

    override suspend fun checkFavoriteBook(isbn: String): Boolean {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "BookRepositoryImpl"
    }
}