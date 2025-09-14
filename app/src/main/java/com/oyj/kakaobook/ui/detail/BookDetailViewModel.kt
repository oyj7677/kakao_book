package com.oyj.kakaobook.ui.detail

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import com.oyj.domain.usecase.CheckBookmarkUseCase
import com.oyj.domain.usecase.DeleteBookmarkUseCase
import com.oyj.domain.usecase.InsertBookmarkUseCase
import com.oyj.kakaobook.mapper.PresenterMapper.toBookItemDetail
import com.oyj.kakaobook.data.BookItemDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val checkBookmarkUseCase: CheckBookmarkUseCase,
    private val insertBookmarkUseCase: InsertBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
) : ViewModel() {

    private val _bookItemDetail = MutableStateFlow(
        BookItemDetail(
            isbn = "",
            title = "",
            author = emptyList(),
            translator = emptyList(),
            publisher = "",
            price = 0,
            salePrice = 0,
            dateTime = "",
            thumbnail = "",
            contents = "",
            isBookmark = false,
        )
    )
    val bookItemDetail: StateFlow<BookItemDetail> = _bookItemDetail

    private lateinit var book: Book

    fun setBook(book: Book) {
        this.book = book
    }

    fun updateBookItemDetail() {
        viewModelScope.launch {
            val isbn = book.isbn
            val resultFlow = checkBookmarkUseCase(isbn)

            resultFlow.collect { result ->
                when (result) {
                    is Result.Success -> {
                        val isBookmarked = result.data
                        _bookItemDetail.value = book.toBookItemDetail(isBookmarked)
                    }

                    is Result.Error -> {
                        Log.e(TAG, "updateBookItemDetail: ${result.throwable.stackTrace}")
                    }
                }
            }
        }
    }

    fun updateBookmark() {
        if (_bookItemDetail.value.isBookmark) {
            deleteBookmark()
        } else {
            insertBookmark()
        }
    }

    private fun insertBookmark() {
        viewModelScope.launch {
            insertBookmarkUseCase(book).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _bookItemDetail.value = book.toBookItemDetail(true)
                    }

                    is Result.Error -> {
                        Log.e(TAG, "insertBookmark: ${result.throwable.stackTrace}")
                    }
                }
            }
        }
    }

    private fun deleteBookmark() {
        viewModelScope.launch {
            deleteBookmarkUseCase(book.isbn).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _bookItemDetail.value = book.toBookItemDetail(false)
                    }

                    is Result.Error -> {
                        Log.e(TAG, "deleteBookmark: ${result.throwable.stackTrace}")
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "BookDetailViewModel"
    }
}