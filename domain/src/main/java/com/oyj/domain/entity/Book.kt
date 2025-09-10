package com.oyj.domain.entity

data class Book(
    val isbn: String,
    val title: String,
    val author: List<String>,
    val translator: List<String>,
    val publisher: String,
    val price: Int,
    val salePrice: Int,
    val dateTime: String,
    val thumbnail: String,
    val contents: String
)
