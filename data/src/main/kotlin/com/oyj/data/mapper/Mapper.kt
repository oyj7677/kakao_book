package com.oyj.data.mapper

import com.oyj.data.dto.BookDto
import com.oyj.data.dto.Document
import com.oyj.domain.entity.Book

object Mapper {
    fun BookDto.toEntityList(): List<Book> =
        documents.map { it.toEntity() }

    fun Document.toEntity() = Book(
        isbn = isbn,
        title = title,
        author = authors,
        translator = translators,
        publisher = publisher,
        price = price,
        salePrice = salePrice,
        dateTime = datetime,
        thumbnail = thumbnail,
        contents = contents
    )
}