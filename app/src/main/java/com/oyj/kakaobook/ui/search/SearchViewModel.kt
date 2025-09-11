package com.oyj.kakaobook.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyj.domain.entity.Result
import com.oyj.domain.usecase.BatchCheckBookmarkUseCase
import com.oyj.domain.usecase.DeleteBookmarkUseCase
import com.oyj.domain.usecase.GetBookListUseCase
import com.oyj.domain.usecase.InsertBookmarkUseCase
import com.oyj.kakaobook.mapper.PresenterMapper.toBookModelListWithBookmarks
import com.oyj.kakaobook.mapper.PresenterMapper.updateBookmarkStates
import com.oyj.kakaobook.model.BookItem
import com.oyj.kakaobook.model.BookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    private val insertBookmarkUseCase: InsertBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val batchCheckBookmarkUseCase: BatchCheckBookmarkUseCase,
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

    // 현재 화면에 표시된 책들의 북마크 상태 (ISBN을 키로 하는 맵)
    private val _bookmarkStates = MutableStateFlow<Map<String, Boolean>>(emptyMap())

    // 검색 결과와 북마크 상태를 결합한 최종 데이터
    val bookList: StateFlow<List<BookItem>> = combine(
        _bookModelList,
        _bookmarkStates
    ) { books, bookmarkStates ->
        books.updateBookmarkStates(bookmarkStates)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun setQuery(query: String) {
        _query.value = query
    }

    private suspend fun searchBooks(query: String) {
        getBookListUseCase.invoke(query).collect { result ->
            when (result) {
                is Result.Success -> {
                    Log.d(TAG, "searchBooks: ${result.data}")
                    _bookModelList.value = result.data.toBookModelListWithBookmarks(emptyMap())
                    // 새로 검색된 책들의 북마크 상태를 배치로 확인
                    loadBookmarkStatesForCurrentBooks(result.data.map { book -> book.isbn })
                }

                is Result.Error -> {
                    Log.e(TAG, "searchBooks: ${result.throwable}", )
                    // TODO: 에러 처리
                }
            }
        }
    }

    /**
     * 현재 화면의 책들에 대한 북마크 상태를 배치로 확인
     * @param isbns 확인할 ISBN 리스트
     */
    private fun loadBookmarkStatesForCurrentBooks(isbns: List<String>) {
        viewModelScope.launch {
            batchCheckBookmarkUseCase.invoke(isbns).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _bookmarkStates.value = result.data
                        Log.d(TAG, "loadBookmarkStates: ${result.data}")
                    }

                    is Result.Error -> {
                        Log.e(TAG, "loadBookmarkStatesForCurrentBooks: ${result.throwable}", )
                        // TODO: 에러 처리
                    }
                }
            }
        }
    }

    fun insertBookmark(isbn: String) {
        val bookModel = _bookModelList.value.find { it.book.isbn == isbn } ?: return
        viewModelScope.launch {
            insertBookmarkUseCase.invoke(bookModel.book).collect {result ->
                when (result) {
                    is Result.Success -> {
                        Log.d(TAG, "insertBookmark: ${result.data}")
                        // 로컬 북마크 상태 업데이트 (즉시 UI 반영)
                        updateLocalBookmarkState(isbn, true)
                    }

                    is Result.Error -> {
                        Log.e(TAG, "insertBookmark: ${result.throwable}", )
                        // TODO: 에러 처리
                    }
                }
            }
        }
    }

    fun deleteBookmark(isbn: String) {
        viewModelScope.launch {
            deleteBookmarkUseCase.invoke(isbn).collect {result ->
                when (result) {
                    is Result.Success -> {
                        Log.d(TAG, "deleteBookmark: ${result.data}")
                        // 로컬 북마크 상태 업데이트 (즉시 UI 반영)
                        updateLocalBookmarkState(isbn, false)
                    }

                    is Result.Error -> {
                        Log.e(TAG, "deleteBookmark: ${result.throwable}", )
                        // TODO: 에러 처리
                    }
                }
            }
        }
    }

    /**
     * 로컬 북마크 상태 업데이트 (즉시 UI 반영용)
     * @param isbn 업데이트할 책의 ISBN
     * @param isBookmarked 북마크 상태
     */
    private fun updateLocalBookmarkState(isbn: String, isBookmarked: Boolean) {
        val currentStates = _bookmarkStates.value.toMutableMap()
        currentStates[isbn] = isBookmarked
        _bookmarkStates.value = currentStates
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}