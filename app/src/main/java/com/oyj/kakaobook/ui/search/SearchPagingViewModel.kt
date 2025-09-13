package com.oyj.kakaobook.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import com.oyj.domain.entity.SortCriteria
import com.oyj.domain.usecase.DeleteBookmarkUseCase
import com.oyj.domain.usecase.GetBookListPagingUseCase
import com.oyj.domain.usecase.GetBookmarkedIsbnsUseCase
import com.oyj.domain.usecase.InsertBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchPagingViewModel @Inject constructor(
    private val getBookListPagingUseCase: GetBookListPagingUseCase,
    private val getBookmarkedIsbnsUseCase: GetBookmarkedIsbnsUseCase,
    private val insertBookmarkUseCase: InsertBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _bookmarkedIsbnSet = MutableStateFlow<Set<String>>(emptySet())
    val bookmarkedIsbnSet: StateFlow<Set<String>> = _bookmarkedIsbnSet

    private val _bookList = MutableStateFlow<PagingData<Book>>(PagingData.empty())

    @OptIn(FlowPreview::class)
    val bookList: StateFlow<PagingData<Book>> = _bookList
        .debounce(1000L)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty(),
        )

    private val _sortCriteria = MutableStateFlow<SortCriteria>(SortCriteria.Accuracy)
    val sortCriteria: StateFlow<SortCriteria> = _sortCriteria

    init {
        observeSearchParameters()
        viewModelScope.launch {
            updateBookmarkedIsbns()
        }
    }

    private fun observeSearchParameters() {
        viewModelScope.launch {
            combine(_query, _sortCriteria) { query, sortCriteria ->
                Pair(query, sortCriteria)
            }.debounce(500)
                .filter { (query, _) -> query.isNotBlank() }
                .distinctUntilChanged()
                .flatMapLatest { (query, sortCriteria) ->
                    Log.d(TAG, "새로운 검색 요청: query=$query, sort=$sortCriteria")
                    getBookListPagingUseCase(query, sortCriteria)
                }.collect {
                    _bookList.value = it
                }
        }
    }

    fun setQuery(keyword: String) {
        Log.d(TAG, "setQuery: $keyword")
        _query.value = keyword
    }

    fun setSortCriteria(sortCriteria: SortCriteria) {
        if (sortCriteria == _sortCriteria.value) return
        _sortCriteria.value = sortCriteria
    }

    fun updateBookmark(book: Book) {
        viewModelScope.launch {
            try {
                if (bookmarkedIsbnSet.value.contains(book.isbn)) {
                    deleteBookmark(book)
                } else {
                    insertBookmark(book)
                }
                // 북마크 작업 완료 후 상태 업데이트
                updateBookmarkedIsbns()
            } catch (e: Exception) {
                Log.e(TAG, "updateBookmark failed: ${e.message}")
            }
        }
    }

    private suspend fun insertBookmark(book: Book) {
        insertBookmarkUseCase.invoke(book).collect { result ->
            when (result) {
                is Result.Success -> {
                    Log.d(TAG, "insertBookmark: ${result.data}")
                    val newIsbnSet = _bookmarkedIsbnSet.value.toMutableSet()
                    newIsbnSet.add(book.isbn)
                    _bookmarkedIsbnSet.value = newIsbnSet
                }
                is Result.Error -> {
                    Log.e(TAG, "insertBookmark: ${result.throwable}")
                    throw result.throwable
                }
            }
        }
    }

    private suspend fun deleteBookmark(book: Book) {
        deleteBookmarkUseCase.invoke(book.isbn).collect { result ->
            when (result) {
                is Result.Success -> {
                    Log.d(TAG, "deleteBookmark: ${result.data}")
                    val newIsbnSet = _bookmarkedIsbnSet.value.toMutableSet()
                    newIsbnSet.remove(book.isbn)
                    _bookmarkedIsbnSet.value = newIsbnSet
                }
                is Result.Error -> {
                    Log.e(TAG, "deleteBookmark: ${result.throwable}")
                    throw result.throwable
                }
            }
        }
    }

    private suspend fun updateBookmarkedIsbns() {
        getBookmarkedIsbnsUseCase.invoke().collect { result ->
            when (result) {
                is Result.Success -> {
                    Log.d(TAG, "getAllBookmarkedIsbns: ${result.data.size}")
                    _bookmarkedIsbnSet.value = result.data
                }
                is Result.Error -> {
                    Log.e(TAG, "getAllBookmarkedIsbns: ${result.throwable}")
                }
            }
        }
    }

    companion object {
        private const val TAG = "SearchPagingViewModel"
    }
}