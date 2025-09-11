package com.oyj.kakaobook.mapper

import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import com.oyj.kakaobook.model.BookItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object PresenterMapper {

    fun Book.toBookItem(isBookmarked: Boolean = false): BookItem {
        return BookItem(
            category = "도서", // Book 엔티티에 category가 없어 빈 값 설정
            title = title,
            publisher = publisher,
            authors = author,
            thumbnail = thumbnail,
            price = price,
            isBookmark = isBookmarked
        )
    }

    suspend fun List<Book>.toBookItemList(): List<BookItem> {
        return map { book ->
            book.toBookItem(isBookmarked = false)
        }
    }
}