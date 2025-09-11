package com.oyj.data.network

import com.oyj.data.dto.BookDto
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoSearchApi {
    @GET("v3/search/book")
    suspend fun searchBooks(
        @Query("query") query: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
    ): BookDto
}