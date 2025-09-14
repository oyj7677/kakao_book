package com.oyj.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Document(
    @SerialName("authors")
    val authors: List<String>,
    @SerialName("contents")
    val contents: String,
    @SerialName("datetime")
    val datetime: String,
    @SerialName("isbn")
    val isbn: String,
    @SerialName("price")
    val price: Int,
    @SerialName("publisher")
    val publisher: String,
    @SerialName("sale_price")
    val salePrice: Int,
    @SerialName("status")
    val status: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("title")
    val title: String,
    @SerialName("translators")
    val translators: List<String>,
    @SerialName("url")
    val url: String
)