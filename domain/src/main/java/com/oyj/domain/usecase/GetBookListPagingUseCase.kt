package com.oyj.domain.usecase

import androidx.paging.PagingData
import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import com.oyj.domain.entity.SortCriteria
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookListPagingUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(query: String, sortCriteria: SortCriteria): Flow<PagingData<Book>> {
        return repository.getBookListPaging(
            query = query,
            sortCriteria = sortCriteria
        )
    }
}