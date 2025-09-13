package com.oyj.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oyj.data.mapper.Mapper.toDomainList
import com.oyj.data.source.remote.BookRemoteSource
import com.oyj.domain.entity.Book
import com.oyj.domain.entity.SortCriteria
import javax.inject.Inject

class RemotePagingSource @Inject constructor(
    private val bookRemoteSource: BookRemoteSource,
    private val query: String,
    private val sortCriteria: SortCriteria
) : PagingSource<Int, Book>() {
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val page = params.key ?: 1
        val size = 20
        return try {
            val bookDto = bookRemoteSource.getBookListWithPaging(
                query = query,
                page = page,
                size = size,
                sort = sortCriteria.value
            )
            val bookList = bookDto.toDomainList().distinctBy { it.isbn }
            val isEnd = bookDto.meta.isEnd

            LoadResult.Page(
                data = bookList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (isEnd) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}