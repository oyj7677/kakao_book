package com.oyj.kakaobook.model

data class BookModel(
    val isbn: String,
    val title: String,
    val author: List<String>,
    val publisher: String,
    val price: Int,
    val salePrice: Int,
    val dateTime: String,
    val thumbnail: String,
    val contents: String,
)
