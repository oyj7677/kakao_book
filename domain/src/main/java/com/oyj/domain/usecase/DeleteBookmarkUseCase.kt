package com.oyj.domain.usecase

import com.oyj.domain.repository.BookRepository

class DeleteBookmarkUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(isbn: String) {
        repository.deleteBookmark(
            isbn = isbn
        )
    }
}