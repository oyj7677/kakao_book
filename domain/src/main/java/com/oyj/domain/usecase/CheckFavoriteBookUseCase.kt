package com.oyj.domain.usecase

import com.oyj.domain.repository.BookRepository

class CheckFavoriteBookUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(isbn: String): Boolean {
        return repository.checkFavoriteBook(
            isbn = isbn
        )
    }
}