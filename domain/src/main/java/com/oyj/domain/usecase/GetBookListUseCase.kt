package com.oyj.domain.usecase

import com.oyj.domain.entity.BookEntity
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetBookListUseCase (
    private val repository: BookRepository
) {
    suspend operator fun invoke(query: String, sort: String): Flow<List<BookEntity>> {
        return repository.getBookList(
            query = query,
            sort = sort
        )
    }
}