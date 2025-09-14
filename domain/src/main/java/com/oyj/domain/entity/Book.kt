package com.oyj.domain.entity

import kotlinx.serialization.Serializable

@Serializable
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
