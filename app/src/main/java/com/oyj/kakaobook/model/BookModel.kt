package com.oyj.kakaobook.model

import com.oyj.domain.entity.Book

data class BookModel(
    val bookItem: BookItem,
    val book: Book
)