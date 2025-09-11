package com.oyj.domain.usecase

import com.oyj.domain.entity.Result
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class DeleteBookmarkUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(isbn: String): Flow<Result<Boolean>> {
        return repository.deleteBookmark(isbn = isbn)
    }
}