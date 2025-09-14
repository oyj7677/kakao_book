package com.oyj.kakaobook.mapper


import com.oyj.domain.entity.Book
import com.oyj.kakaobook.data.BookItem
import com.oyj.kakaobook.data.BookItemDetail
import com.oyj.kakaobook.model.BookModel

object PresenterMapper {

    fun Book.toBookItem(isBookmarked: Boolean = false): BookItem {
        return BookItem(
            isbn = isbn,
            category = "도서",
            title = title,
            publisher = publisher,
            authors = author,
            thumbnail = thumbnail,
            price = price,
            dateTime = dateTime,
            isBookmark = isBookmarked
        )
    }

    fun Book.toBookModel(isBookmarked: Boolean = false): BookModel {
        return BookModel(
            bookItem = toBookItem(isBookmarked),
            book = this
        )
    }

    fun Book.toBookItemDetail(isBookmarked: Boolean = false): BookItemDetail {
        return BookItemDetail(
            isbn = isbn,
            title = title,
            author = author,
            translator = translator,
            publisher = publisher,
            price = price,
            salePrice = salePrice,
            dateTime = dateTime,
            thumbnail = thumbnail,
            contents = contents,
            isBookmark = isBookmarked
        )
    }
}