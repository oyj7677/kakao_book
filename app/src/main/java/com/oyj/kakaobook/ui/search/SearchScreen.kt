package com.oyj.kakaobook.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oyj.kakaobook.model.BookItem
import com.oyj.kakaobook.model.SearchUiState
import com.oyj.kakaobook.model.SortCriteria
import com.oyj.kakaobook.model.Success
import com.oyj.kakaobook.ui.component.SearchStateView
import com.oyj.kakaobook.ui.component.SortCriteriaSelector
import com.oyj.kakaobook.ui.component.TitleTopBar
import com.oyj.kakaobook.R

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel()
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()
    val sortCriteria by viewModel.selectedCriteria.collectAsStateWithLifecycle()

    SearchScreen(
        modifier = modifier,
        query = query,
        searchUiState = searchUiState,
        selectedCriteria = sortCriteria,
        onQueryChanged = viewModel::setQuery,
        onCriteriaSelected = { criteria ->
            viewModel.setSortCriteria(criteria)
        },
        onClickBookmark = {
            viewModel.updateBookmark(it)
        }
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    query: String,
    searchUiState: SearchUiState,
    selectedCriteria: SortCriteria,
    onQueryChanged: (String) -> Unit = {},
    onCriteriaSelected: (SortCriteria) -> Unit = {},
    onClickBookmark: (String) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TitleTopBar(title = stringResource(id = R.string.title_search))
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 검색바
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { stringResource(id = R.string.hint_search) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.description_searchbar_icon)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // 정렬 기준
            SortCriteriaSelector(
                selectedCriteria = selectedCriteria,
                onCriteriaSelected = onCriteriaSelected
            )

            SearchStateView(
                searchUiState = searchUiState,
                query = query,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                onClickBookmark = onClickBookmark
            )
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        query = "test",
        searchUiState = Success(
            bookList = emptyList()
        ),
        selectedCriteria = SortCriteria.Latest
    )
}
@Preview
@Composable
private fun SearchScreenWithBooksPreview() {
    val sampleBooks = listOf(
        BookItem(
            isbn = "9788950982264",
            category = "소설",
            title = "미드나잇 라이브러리",
            publisher = "인플루엔셜",
            authors = listOf("매트 헤이그"),
            thumbnail = "",
            price = 13320,
            dateTime = "2014-11-17T00:00:00.000+09:00",
            isBookmark = false
        ),
        BookItem(
            isbn = "9788950982264",
            category = "에세이",
            title = "아몬드",
            publisher = "창비",
            authors = listOf("손원평"),
            thumbnail = "",
            price = 12600,
            dateTime = "2014-11-17T00:00:00.000+09:00",
            isBookmark = true
        ),
        BookItem(
            isbn = "9788950982264",
            category = "자기계발",
            title = "원씽 The One Thing",
            publisher = "비즈니스북스",
            authors = listOf("게리 켈러", "제이 파파산"),
            thumbnail = "",
            price = 14400,
            dateTime = "2014-11-17T00:00:00.000+09:00",
            isBookmark = false
        )
    )

    SearchScreen(
        query = "미드나잇",
        searchUiState = Success(
            bookList = sampleBooks,
        ),
        selectedCriteria = SortCriteria.Latest
    )
}