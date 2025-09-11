package com.oyj.data.mapper

import com.oyj.data.dto.BookDto
import com.oyj.data.dto.Document
import com.oyj.data.database.entity.BookmarkEntity
import com.oyj.domain.entity.Book

object Mapper {
    fun BookDto.toDomainList(): List<Book> =
        documents.map { it.toDomain() }

    fun Document.toDomain() = Book(
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

    fun List<BookmarkEntity>.toDomainList(): List<Book> =
        map { it.toDomain() }

    fun BookmarkEntity.toDomain() = Book(
        isbn = isbn,
        title = title,
        author = author,
        translator = translator,
        publisher = publisher,
        price = price,
        salePrice = salePrice,
        dateTime = dateTime,
        thumbnail = thumbnail,
        contents = contents
    )

    fun Book.toData() = BookmarkEntity(
        isbn = isbn,
        title = title,
        author = author,
        translator = translator,
        publisher = publisher,
        price = price,
        salePrice = salePrice,
        dateTime = dateTime,
        thumbnail = thumbnail,
        contents = contents
    )
}