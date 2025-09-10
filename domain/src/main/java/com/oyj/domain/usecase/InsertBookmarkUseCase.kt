package com.oyj.domain.usecase

import com.oyj.domain.entity.Book
import com.oyj.domain.repository.BookRepository

class InsertBookmarkUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(book: Book) {
        repository.insertBookmark(book)
    }
}