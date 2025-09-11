package com.oyj.data.impl

import android.util.Log
import android.util.LruCache
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
) : BookRepository {

    // LRU 캐시: 최근 확인한 북마크 상태를 메모리에 보관 (최대 500개)
    // 즐겨찾기 최대치 설정
    private val bookmarkCache = LruCache<String, Boolean>(500)

    override suspend fun getBookList(
        query: String
    ): Flow<Result<List<Book>>> {
        Log.d(TAG, "getBookList: query = $query")
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

    override suspend fun getBookmarkList(): Flow<Result<List<Book>>> {
        return flow {
            runCatching {
                val bookList = bookLocalSource.getBookmarkList().toDomainList()
                emit(Result.Success(bookList))
            }.onFailure {
                Log.e(TAG, "getBookmarkList: ${it.message}")
                emit(Result.Error(it))
            }
        }
    }

    override suspend fun insertBookmark(book: Book): Flow<Result<Boolean>> {
        return flow {
            runCatching {
                bookLocalSource.insertBookmark(book.toData())
                // 캐시 업데이트
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
                // 캐시 업데이트
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
                // 1. 캐시에서 먼저 확인
                bookmarkCache.get(isbn)?.let { cached ->
                    emit(Result.Success(cached))
                    return@runCatching
                }

                // 2. DB에서 조회 후 캐시에 저장
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

                // 1. 캐시에서 먼저 확인
                isbns.forEach { isbn ->
                    bookmarkCache.get(isbn)?.let { cached ->
                        results[isbn] = cached
                    } ?: uncachedIsbns.add(isbn)
                }

                // 2. DB에서 캐시되지 않은 ISBN들 일괄 조회
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

    companion object {
        private const val TAG = "BookRepositoryImpl"
    }
}