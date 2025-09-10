package com.oyj.domain.usecase

import com.oyj.domain.entity.Book
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(query: String, sort: String): Flow<List<Book>> {
        return repository.getBookList(
            query = query,
            sort = sort
        )
    }
}