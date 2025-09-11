package com.oyj.domain.usecase

import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import com.oyj.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertBookmarkUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(book: Book): Flow<Result<Boolean>> {
        return repository.insertBookmark(book)
    }
}