package com.oyj.data.source.remote

import com.oyj.data.dto.BookDto

interface BookRemoteSource {
        suspend fun getBookList(query: String) : BookDto
}