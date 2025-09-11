package com.oyj.kakaobook.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyj.domain.entity.Result
import com.oyj.domain.usecase.GetBookListUseCase
import com.oyj.kakaobook.mapper.PresenterMapper.toBookItemList
import com.oyj.kakaobook.model.BookItem
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
    private val getBookListUseCase: GetBookListUseCase
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

    private val _bookItemList = MutableStateFlow<List<BookItem>>(emptyList())
    val bookList: StateFlow<List<BookItem>> = _bookItemList


    fun setQuery(query: String) {
        _query.value = query
    }

    private suspend fun searchBooks(query: String) {
        getBookListUseCase.invoke(query).collect {
            when (it) {
                is Result.Success -> {
                    Log.d(TAG, "searchBooks: ${it.data}")
                    _bookItemList.value = it.data.toBookItemList()
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