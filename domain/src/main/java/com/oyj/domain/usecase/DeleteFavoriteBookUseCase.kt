package com.oyj.domain.usecase

import com.oyj.domain.repository.BookRepository

class DeleteFavoriteBookUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(isbn: String) {
        repository.deleteFavoriteBook(
            isbn = isbn
        )
    }
}