package com.oyj.kakaobook.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.oyj.domain.entity.Book
import com.oyj.domain.entity.SortCriteria
import com.oyj.domain.usecase.GetBookListPagingUseCase
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

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchPagingViewModel @Inject constructor(
    private val getBookListPagingUseCase: GetBookListPagingUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

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
        viewModelScope.launch {
            _query.debounce(500)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collect { query ->
                    getBookListPagingUseCase(query, SortCriteria.Accuracy).collect {
                        _bookList.value = it
                    }
                }
        }
    }

    fun setQuery(keyword: String) {
        _query.value = keyword
    }

    fun setSortCriteria(sortCriteria: SortCriteria) {
        if (sortCriteria == _sortCriteria.value) return
        _sortCriteria.value = sortCriteria
    }

    companion object {
        private const val TAG = "SearchPagingViewModel"
    }
}