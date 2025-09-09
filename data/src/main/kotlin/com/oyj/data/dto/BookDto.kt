package com.oyj.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    @SerialName("documents")
    val documents: List<Document>,
    @SerialName("meta")
    val meta: Meta
)