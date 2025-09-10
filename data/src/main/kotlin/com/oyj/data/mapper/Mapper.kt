package com.oyj.data.mapper

import com.oyj.data.dto.BookDto
import com.oyj.data.dto.Document
import com.oyj.domain.entity.BookEntity

object Mapper {
    fun BookDto.toEntityList(): List<BookEntity> =
        documents.map { it.toEntity() }

    fun Document.toEntity() = BookEntity(
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