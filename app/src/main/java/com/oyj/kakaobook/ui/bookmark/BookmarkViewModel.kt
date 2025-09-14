package com.oyj.kakaobook.ui.bookmark

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oyj.domain.entity.Book
import com.oyj.domain.entity.Result
import com.oyj.kakaobook.data.SearchSortCriteria
import com.oyj.domain.usecase.DeleteBookmarkUseCase
import com.oyj.domain.usecase.GetBookmarkPagingUseCase
import com.oyj.domain.usecase.GetBookmarkedIsbnsUseCase
import com.oyj.domain.usecase.InsertBookmarkUseCase
import com.oyj.kakaobook.data.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkPagingUseCase: GetBookmarkPagingUseCase,
    private val getBookmarkedIsbnsUseCase: GetBookmarkedIsbnsUseCase,
    private val insertBookmarkUseCase: InsertBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _bookmarkedIsbnSet = MutableStateFlow<Set<String>>(emptySet())
    val bookmarkedIsbnSet: StateFlow<Set<String>> = _bookmarkedIsbnSet

    private val _searchSortCriteria = MutableStateFlow<SearchSortCriteria>(SearchSortCriteria.Title)
    val searchSortCriteria: StateFlow<SearchSortCriteria> = _searchSortCriteria

    private val _sortOrder = MutableStateFlow<SortOrder>(SortOrder.Ascending)

    @OptIn(FlowPreview::class)
    val bookList: StateFlow<PagingData<Book>> =
        combine(_query, _searchSortCriteria, _sortOrder) { query, sortCriteria, sortOrder ->
            Triple(query, sortCriteria, sortOrder)
        }.debounce(500)
            .distinctUntilChanged()
            .flatMapLatest { (query, sortCriteria, sortOrder) ->
                Log.d(TAG, "${sortCriteria.value}_${sortOrder.value}")
                getBookmarkPagingUseCase(query, "${sortCriteria.value}_${sortOrder.value}")
            }.cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = PagingData.empty(),
            )

    init {
        viewModelScope.launch {
            updateBookmarkedIsbns()
        }
    }

    fun setQuery(keyword: String) {
        _query.value = keyword
    }

    fun setSortCriteria(searchSortCriteria: SearchSortCriteria) {
        if (searchSortCriteria == _searchSortCriteria.value) {
            changeSortOrder()
        } else {
            _searchSortCriteria.value = searchSortCriteria
        }
    }

    private fun changeSortOrder() {
        _sortOrder.value = if (_sortOrder.value == SortOrder.Ascending) {
            SortOrder.Descending
        } else {
            SortOrder.Ascending
        }
    }

    fun updateBookmark(book: Book) {
        viewModelScope.launch {
            // 1. 즉시 UI 상태 업데이트 (optimistic update)
            val currentBookmarks = _bookmarkedIsbnSet.value.toMutableSet()
            val isCurrentlyBookmarked = currentBookmarks.contains(book.isbn)

            if (isCurrentlyBookmarked) {
                currentBookmarks.remove(book.isbn)
            } else {
                currentBookmarks.add(book.isbn)
            }
            _bookmarkedIsbnSet.value = currentBookmarks

            // 2. 백그라운드에서 실제 DB 작업 수행
            try {
                if (isCurrentlyBookmarked) {
                    deleteBookmarkInBackground(book)
                } else {
                    insertBookmarkInBackground(book)
                }
            } catch (e: Exception) {
                // 3. 실패 시 UI 상태 롤백
                Log.e(TAG, "updateBookmark failed: ${e.message}")
                val rollbackBookmarks = _bookmarkedIsbnSet.value.toMutableSet()
                if (isCurrentlyBookmarked) {
                    rollbackBookmarks.add(book.isbn) // 삭제 실패 시 다시 추가
                } else {
                    rollbackBookmarks.remove(book.isbn) // 추가 실패 시 다시 제거
                }
                _bookmarkedIsbnSet.value = rollbackBookmarks
            }
        }
    }

    private suspend fun insertBookmarkInBackground(book: Book) {
        insertBookmarkUseCase.invoke(book).collect { result ->
            when (result) {
                is Result.Success -> {
                    Log.d(TAG, "insertBookmark success: ${result.data}")
                    // UI는 이미 업데이트되었으므로 추가 작업 없음
                }

                is Result.Error -> {
                    Log.e(TAG, "insertBookmark error: ${result.throwable}")
                    throw result.throwable
                }
            }
        }
    }

    private suspend fun deleteBookmarkInBackground(book: Book) {
        deleteBookmarkUseCase.invoke(book.isbn).collect { result ->
            when (result) {
                is Result.Success -> {
                    Log.d(TAG, "deleteBookmark success: ${result.data}")
                    // UI는 이미 업데이트되었으므로 추가 작업 없음
                }

                is Result.Error -> {
                    Log.e(TAG, "deleteBookmark error: ${result.throwable}")
                    throw result.throwable
                }
            }
        }
    }

    suspend fun updateBookmarkedIsbns() {
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
        private const val TAG = "BookmarkPagingViewModel"
    }
}