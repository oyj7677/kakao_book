package com.oyj.domain.usecase

import com.oyj.domain.repository.BookRepository

class CheckBookmarkUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(isbn: String): Boolean {
        return repository.checkBookmark(
            isbn = isbn
        )
    }
}