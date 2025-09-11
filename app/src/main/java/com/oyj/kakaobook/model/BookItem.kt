package com.oyj.kakaobook.model

data class BookItem(
    val category: String,
    val title : String,
    val publisher: String,
    val authors: List<String>,
    val thumbnail: String,
    val price: Int,
    val isBookmark: Boolean
)
