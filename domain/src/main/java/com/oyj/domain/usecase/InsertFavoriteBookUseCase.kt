package com.oyj.domain.usecase

import com.oyj.domain.entity.BookEntity
import com.oyj.domain.repository.BookRepository

class InsertFavoriteBookUseCase (
    private val repository: BookRepository
) {
    suspend operator fun invoke(bookEntity: BookEntity) {
        repository.insertFavoriteBook(bookEntity)
    }
}