package com.oyj.domain.usecase

import com.oyj.domain.entity.BookEntity
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteBookListUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(): Flow<List<BookEntity>> {
        return repository.getFavoriteBookList()
    }
}