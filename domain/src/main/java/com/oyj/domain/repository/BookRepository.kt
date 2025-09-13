package com.oyj.domain.repository

import androidx.paging.PagingData
import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import com.oyj.domain.entity.SortCriteria
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBookList(query: String): Flow<Result<List<Book>>>
    suspend fun getBookListPaging(query: String, sortCriteria: SortCriteria): Flow<PagingData<Book>>

    suspend fun getBookmarkList(): Flow<Result<List<Book>>>

    suspend fun insertBookmark(book: Book): Flow<Result<Boolean>>

    suspend fun deleteBookmark(isbn: String): Flow<Result<Boolean>>

    suspend fun checkBookmark(isbn: String): Flow<Result<Boolean>>

    suspend fun batchCheckBookmarks(isbns: List<String>): Flow<Result<Map<String, Boolean>>>

    suspend fun getAllBookmarkedIsbns(): Flow<Result<Set<String>>>
}