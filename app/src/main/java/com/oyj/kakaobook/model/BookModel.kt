package com.oyj.kakaobook.model

import com.oyj.domain.entity.Book
import com.oyj.kakaobook.data.BookItem

data class BookModel(
    val bookItem: BookItem,
    val book: Book
)