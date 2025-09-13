package com.oyj.data.source.remote

import com.oyj.data.dto.BookDto

interface BookRemoteSource {
    suspend fun getBookList(query: String): BookDto
    suspend fun getBookListWithPaging(query: String, page: Int, size: Int, sort: String): BookDto
}