package com.oyj.domain.usecase

import com.oyj.domain.entity.Result
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkedIsbnsUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(): Flow<Result<Set<String>>> {
        return repository.getAllBookmarkedIsbns()
    }
}
