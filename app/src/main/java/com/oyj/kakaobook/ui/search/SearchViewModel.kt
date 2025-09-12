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
import com.oyj.kakaobook.model.BookModel
import com.oyj.kakaobook.model.Empty
import com.oyj.kakaobook.model.SearchUiState
import com.oyj.kakaobook.model.Success
import com.oyj.kakaobook.model.Error
import com.oyj.kakaobook.model.Init
import com.oyj.kakaobook.model.Loading
import com.oyj.kakaobook.model.SortCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
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

    // UI에 즉시 반영되는 query (필터링 없음)
    val query: StateFlow<String> = _query.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    private val _bookModelList = MutableStateFlow<List<BookModel>>(emptyList())

    // 현재 화면에 표시된 책들의 북마크 상태 (ISBN을 키로 하는 맵)
    private val _bookmarkStates = MutableStateFlow<Map<String, Boolean>>(emptyMap())

    // 에러 상태를 별도로 관리
    private val _errorState = MutableStateFlow<String?>(null)

    // 로딩 상태를 별도로 관리
    private val _isLoading = MutableStateFlow(false)

    // 정렬 상태
    private val _selectedCriteria = MutableStateFlow<SortCriteria>(SortCriteria.Latest)
    val selectedCriteria: StateFlow<SortCriteria> = _selectedCriteria

    // 검색 상태와 북마크 상태를 결합한 최종 UI 상태
    val searchUiState: StateFlow<SearchUiState> = combine(
        _bookModelList,
        _bookmarkStates,
        _errorState,
        _isLoading,
        _query
    ) { books, bookmarkStates, errorMessage, isLoading, query ->
        when {
            errorMessage != null -> Error(errorMessage)
            isLoading -> Loading
            books.isNotEmpty() -> {
                val bookItems = books.updateBookmarkStates(bookmarkStates)
                Success(bookItems)
            }
            query.isNotBlank() && books.isEmpty() -> Empty
            else -> Init
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Init
    )

    init {
        // 검색용 query 구독 (debounce, filter 적용)
        @OptIn(FlowPreview::class)
        viewModelScope.launch {
            _query
                .debounce(1000)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collect { searchQuery ->
                    searchBooks(searchQuery)
                }
        }
    }

    fun setQuery(query: String) {
        _query.value = query
        // 새로운 검색 시작 시 에러 상태 초기화
        _errorState.value = null
    }

    private suspend fun searchBooks(query: String) {
        // 검색 시작 시 로딩 상태로 설정하고 에러 상태 초기화
        _isLoading.value = true
        _errorState.value = null

        getBookListUseCase.invoke(query).collect { result ->
            when (result) {
                is Result.Success -> {
                    Log.d(TAG, "searchBooks: ${result.data}")
                    _bookModelList.value = result.data.toBookModelListWithBookmarks(emptyMap())
                    _isLoading.value = false
                    _errorState.value = null
                    // 새로 검색된 책들의 북마크 상태를 배치로 확인
                    loadBookmarkStatesForCurrentBooks(result.data.map { book -> book.isbn })
                }

                is Result.Error -> {
                    Log.e(TAG, "searchBooks: ${result.throwable}")
                    _isLoading.value = false
                    _bookModelList.value = emptyList()
                    _bookmarkStates.value = emptyMap()
                    _errorState.value = result.throwable.message ?: "검색 중 오류가 발생했습니다"
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
                        Log.e(TAG, "loadBookmarkStatesForCurrentBooks: ${result.throwable}")
                        // 북마크 상태 로드 실패는 검색 결과 표시에 영향주지 않음
                    }
                }
            }
        }
    }

    fun updateBookmark(isbn: String) {
        val isCurrentlyBookmarked = _bookmarkStates.value[isbn] ?: false
        if (isCurrentlyBookmarked) {
            deleteBookmark(isbn)
        } else {
            insertBookmark(isbn)
        }
    }

    private fun insertBookmark(isbn: String) {
        val bookModel = _bookModelList.value.find { it.book.isbn == isbn } ?: return
        viewModelScope.launch {
            insertBookmarkUseCase.invoke(bookModel.book).collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d(TAG, "insertBookmark: ${result.data}")
                        // 로컬 북마크 상태 업데이트 (즉시 UI 반영)
                        updateLocalBookmarkState(isbn, true)
                    }

                    is Result.Error -> {
                        Log.e(TAG, "insertBookmark: ${result.throwable}")
                        // 북마크 추가 실패 시 에러 처리 (필요시 스낵바나 토스트 메시지)
                    }
                }
            }
        }
    }

    private fun deleteBookmark(isbn: String) {
        viewModelScope.launch {
            deleteBookmarkUseCase.invoke(isbn).collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d(TAG, "deleteBookmark: ${result.data}")
                        // 로컬 북마크 상태 업데이트 (즉시 UI 반영)
                        updateLocalBookmarkState(isbn, false)
                    }

                    is Result.Error -> {
                        Log.e(TAG, "deleteBookmark: ${result.throwable}")
                        // 북마크 삭제 실패 시 에러 처리 (필요시 스낵바나 토스트 메시지)
                    }
                }
            }
        }
    }

    /**
     * 에러 상태를 초기화합니다
     */
    fun clearError() {
        _errorState.value = null
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

    fun setSortCriteria(criteria: SortCriteria) {
        updateSortState(criteria)
        applySorting()
    }

    /**
     * 정렬 상태 업데이트
     * 책임: 정렬 기준과 방향 상태만 관리
     */
    private fun updateSortState(criteria: SortCriteria) {
        if (criteria == _selectedCriteria.value) {
            toggleSortDirection()
        } else {
            _selectedCriteria.value = criteria
        }
    }

    /**
     * 정렬 방향 토글
     * 책임: 정렬 방향 변경만 담당
     */
    private fun toggleSortDirection() {
        _selectedCriteria.value.isAscending = !_selectedCriteria.value.isAscending
    }

    /**
     * 정렬 적용
     * 책임: 현재 정렬 상태에 따른 데이터 정렬만 담당
     */
    private fun applySorting() {
        val currentList = _bookModelList.value
        _bookModelList.value = when (_selectedCriteria.value) {
            SortCriteria.Latest -> {
                sortByMultipleCriteria(
                    list = currentList,
                    primarySelector = { it.book.dateTime },
                    secondarySelector = { it.book.title },
                    isAscending = _selectedCriteria.value.isAscending
                )
            }
            SortCriteria.Price -> {
                sortByMultipleCriteria(
                    list = currentList,
                    primarySelector = { it.book.price },
                    secondarySelector = { it.book.title },
                    isAscending = _selectedCriteria.value.isAscending
                )
            }
        }
    }

    /**
     * 범용 정렬 함수
     * 책임: 1차 정렬 + 2차 정렬 + 정렬 방향을 모두 파라미터로 받아 처리
     * @param list 정렬할 리스트
     * @param primarySelector 1차 정렬 기준을 선택하는 함수
     * @param secondarySelector 2차 정렬 기준을 선택하는 함수
     * @param isAscending 정렬 방향 (true: 오름차순, false: 내림차순)
     */
    private fun <T : Comparable<T>, U : Comparable<U>> sortByMultipleCriteria(
        list: List<BookModel>,
        primarySelector: (BookModel) -> T,
        secondarySelector: (BookModel) -> U,
        isAscending: Boolean
    ): List<BookModel> {
        return if (isAscending) {
            list.sortedWith(compareBy(primarySelector).thenBy(secondarySelector))
        } else {
            list.sortedWith(compareByDescending(primarySelector).thenBy(secondarySelector))
        }
    }


    companion object {
        private const val TAG = "SearchViewModel"
    }
}
