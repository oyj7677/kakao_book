package com.oyj.domain.usecase

import com.oyj.domain.entity.Result
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BatchCheckBookmarkUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(isbns: List<String>): Flow<Result<Map<String, Boolean>>> {
        return repository.batchCheckBookmarks(isbns)
    }
}
