package com.oyj.domain.usecase

import androidx.paging.PagingData
import com.oyj.domain.entity.Book
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(query: String, sortCriteria: String): Flow<PagingData<Book>> {
        return repository.getBookList(
            query = query,
            sortCriteria = sortCriteria
        )
    }
}