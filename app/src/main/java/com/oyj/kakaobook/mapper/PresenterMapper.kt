package com.oyj.kakaobook.mapper

import com.oyj.domain.entity.Book
import com.oyj.kakaobook.model.BookItem
import com.oyj.kakaobook.model.BookModel

object PresenterMapper {

    fun Book.toBookItem(isBookmarked: Boolean = false): BookItem {
        return BookItem(
            isbn = isbn,
            category = "도서", // Book 엔티티에 category가 없어 빈 값 설정
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

    fun List<Book>.toBookModelList() : List<BookModel> {
        return map { book ->
            book.toBookModel()
        }
    }

    /**
     * 북마크 상태 맵과 함께 BookModel 리스트로 변환
     * @param bookmarkStates ISBN을 키로 하고 북마크 상태를 값으로 하는 맵
     */
    fun List<Book>.toBookModelListWithBookmarks(bookmarkStates: Map<String, Boolean>): List<BookModel> {
        return map { book ->
            val isBookmarked = bookmarkStates[book.isbn] ?: false
            book.toBookModel(isBookmarked)
        }
    }

    /**
     * BookModel 리스트의 북마크 상태를 업데이트
     * @param bookmarkStates ISBN을 키로 하고 북마크 상태를 값으로 하는 맵
     */
    fun List<BookModel>.updateBookmarkStates(bookmarkStates: Map<String, Boolean>): List<BookItem> {
        return map { bookModel ->
            val isBookmarked = bookmarkStates[bookModel.book.isbn] ?: bookModel.bookItem.isBookmark
            bookModel.bookItem.copy(isBookmark = isBookmarked)
        }
    }
}