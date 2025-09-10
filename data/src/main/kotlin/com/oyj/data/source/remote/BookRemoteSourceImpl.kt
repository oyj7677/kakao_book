package com.oyj.data.source.remote

import com.oyj.data.dto.BookDto
import com.oyj.data.network.KakaoSearchApi
import javax.inject.Inject

class BookRemoteSourceImpl @Inject constructor(
    private val kakaoSearchApi: KakaoSearchApi
) : BookRemoteSource {
    override suspend fun getBookList(query: String): BookDto {
        return kakaoSearchApi.searchBooks(query)
    }

    companion object {
        private const val TAG = "BookRemoteSourceImpl"
    }
}