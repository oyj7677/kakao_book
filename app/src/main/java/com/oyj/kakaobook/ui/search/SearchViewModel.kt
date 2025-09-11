package com.oyj.kakaobook.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyj.domain.entity.Result
import com.oyj.domain.usecase.DeleteBookmarkUseCase
import com.oyj.domain.usecase.GetBookListUseCase
import com.oyj.domain.usecase.InsertBookmarkUseCase
import com.oyj.kakaobook.mapper.PresenterMapper.toBookModelList
import com.oyj.kakaobook.model.BookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    private val insertBookmarkUseCase: InsertBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val query: StateFlow<String> = _query
        .debounce(1000)
        .distinctUntilChanged()
        .filter { it.isNotBlank() }
        .onEach { searchQuery ->
            searchBooks(searchQuery)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    private val _bookModelList = MutableStateFlow<List<BookModel>>(emptyList())
    val bookList: StateFlow<List<BookModel>> = _bookModelList

    fun setQuery(query: String) {
        _query.value = query
    }

    private suspend fun searchBooks(query: String) {
        getBookListUseCase.invoke(query).collect {
            when (it) {
                is Result.Success -> {
                    Log.d(TAG, "searchBooks: ${it.data}")
                    _bookModelList.value = it.data.toBookModelList()
                }

                is Result.Error -> {
                    TODO()
                }
            }
        }
    }

    suspend fun insertBookmark(isbn: String) {
        val bookModel = bookList.value.find { it.book.isbn == isbn } ?: return
        insertBookmarkUseCase.invoke(bookModel.book).collect {
            when (it) {
                is Result.Success -> {
                    Log.d(TAG, "insertBookmark: ${it.data}")
                    // 북마크 추가 성공 처리 (예: UI 업데이트)
                }

                is Result.Error -> {
                    TODO()
                }
            }
        }
    }

    suspend fun deleteBookmark(isbn: String) {
        deleteBookmarkUseCase.invoke(isbn).collect {
            when (it) {
                is Result.Success -> {
                    Log.d(TAG, "deleteBookmark: ${it.data}")
                    // 북마크 삭제 성공 처리 (예: UI 업데이트)
                }

                is Result.Error -> {
                    TODO()
                }
            }
        }
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}