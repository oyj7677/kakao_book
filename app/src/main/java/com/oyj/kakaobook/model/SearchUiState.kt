package com.oyj.kakaobook.model

sealed class SearchUiState

object Init : SearchUiState()
object Loading : SearchUiState()
object Empty : SearchUiState()
data class Success(val bookList: List<BookItem>) : SearchUiState()
data class Error(val message: String) : SearchUiState()

