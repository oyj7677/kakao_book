package com.oyj.data.impl

import android.util.Log
import android.util.LruCache
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.oyj.data.mapper.Mapper.toData
import com.oyj.data.mapper.Mapper.toDomain
import com.oyj.data.source.RemotePagingSource
import com.oyj.data.source.local.BookLocalSource
import com.oyj.data.source.remote.BookRemoteSource
import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookRemoteSource: BookRemoteSource,
    private val bookLocalSource: BookLocalSource
) : BookRepository {

    private val bookmarkCache = LruCache<String, Boolean>(MAX_CACHE_SIZE)

    override suspend fun getBookList(
        query: String,
        sortCriteria: String
    ): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { RemotePagingSource(bookRemoteSource, query, sortCriteria) }
        ).flow
    }

    override fun getBookmarkList(
        query: String,
        sortCriteria: String
    ): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { bookLocalSource.getBookmarkList(query, sortCriteria) }
        ).flow.map { pagingData ->
            pagingData.map{ it.toDomain()}
        }
    }

    override suspend fun insertBookmark(book: Book): Flow<Result<Boolean>> {
        return flow {
            runCatching {
                bookLocalSource.insertBookmark(book.toData())
                bookmarkCache.put(book.isbn, true)
                emit(Result.Success(true))
            }.onFailure {
                Log.e(TAG, "insertBookmark: ${it.message}")
                emit(Result.Error(it))
            }
        }
    }

    override suspend fun deleteBookmark(isbn: String): Flow<Result<Boolean>> {
        return flow {
            runCatching {
                bookLocalSource.deleteBookmark(isbn)
                bookmarkCache.put(isbn, false)
                emit(Result.Success(true))
            }.onFailure {
                Log.e(TAG, "deleteBookmark: ${it.message}")
                emit(Result.Error(it))
            }
        }
    }

    override suspend fun checkBookmark(isbn: String): Flow<Result<Boolean>> {
        return flow {
            runCatching {
                bookmarkCache.get(isbn)?.let { cached ->
                    emit(Result.Success(cached))
                    return@runCatching
                }

                val checkBookmarked = bookLocalSource.checkBookmark(isbn)
                bookmarkCache.put(isbn, checkBookmarked)
                emit(Result.Success(checkBookmarked))
            }.onFailure {
                Log.e(TAG, "checkBookmark: ${it.message}")
                emit(Result.Error(it))
            }
        }
    }

    override suspend fun batchCheckBookmarks(isbns: List<String>): Flow<Result<Map<String, Boolean>>> {
        return flow {
            runCatching {
                val results = mutableMapOf<String, Boolean>()
                val uncachedIsbns = mutableListOf<String>()

                isbns.forEach { isbn ->
                    bookmarkCache.get(isbn)?.let { cached ->
                        results[isbn] = cached
                    } ?: uncachedIsbns.add(isbn)
                }

                if (uncachedIsbns.isNotEmpty()) {
                    uncachedIsbns.forEach { isbn ->
                        val isBookmarked = bookLocalSource.checkBookmark(isbn)
                        bookmarkCache.put(isbn, isBookmarked)
                        results[isbn] = isBookmarked
                    }
                }

                emit(Result.Success(results))
            }.onFailure {
                Log.e(TAG, "batchCheckBookmarks: ${it.message}")
                emit(Result.Error(it))
            }
        }
    }

    override suspend fun getAllBookmarkedIsbns(): Flow<Result<Set<String>>> {
        return flow {
            runCatching {
                val isbnList = bookLocalSource.getAllBookmarkIsbn()
                // 캐시에 모든 북마크 상태 저장
                isbnList.forEach { isbn ->
                    bookmarkCache.put(isbn, true)
                }
                emit(Result.Success(isbnList.toSet()))
            }.onFailure {
                Log.e(TAG, "getAllBookmarkedIsbns: ${it.message}")
                emit(Result.Error(it))
            }
        }
    }


    companion object {
        private const val TAG = "BookRepositoryImpl"
        private const val MAX_CACHE_SIZE = 500
    }
}