package com.oyj.domain.usecase

import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class InsertBookmarkUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(book: Book): Flow<Result<Boolean>> {
        return repository.insertBookmark(book)
    }
}