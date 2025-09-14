package com.oyj.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookmark",
)
data class BookmarkEntity(
    @PrimaryKey
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
