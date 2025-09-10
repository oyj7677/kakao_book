package com.oyj.domain.usecase

import com.oyj.domain.entity.Book
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetBookmarkListUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(): Flow<List<Book>> {
        return repository.getBookmarkList()
    }
}