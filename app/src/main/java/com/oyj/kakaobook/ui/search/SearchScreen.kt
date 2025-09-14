package com.oyj.kakaobook.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.oyj.domain.entity.Book
import com.oyj.kakaobook.data.SearchSortCriteria
import com.oyj.kakaobook.ui.component.TitleTopBar
import com.oyj.kakaobook.R
import com.oyj.kakaobook.ui.component.SearchStateView
import com.oyj.kakaobook.ui.component.SortCriteriaSelector
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val bookList = viewModel.bookList.collectAsLazyPagingItems()
    val sortCriteria by viewModel.searchSortCriteria.collectAsStateWithLifecycle()
    val bookmarkedIsbnSet by viewModel.bookmarkedIsbnSet.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        // 초기 즐겨찾기 데이터 로드
        viewModel.updateBookmarkedIsbns()
    }

    SearchScreen(
        modifier = modifier,
        query = query,
        bookList = bookList,
        bookmarkedIsbnSet = bookmarkedIsbnSet,
        searchSortCriteria = sortCriteria,
        onQueryChanged = viewModel::setQuery,
        onCriteriaSelected = { criteria ->
            viewModel.setSortCriteria(criteria)
        },
        onClickBookmark = {
            viewModel.updateBookmark(it)
        },
        onClickCard = {
            navController.navigate(it)
        }
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    query: String,
    bookList: LazyPagingItems<Book>,
    bookmarkedIsbnSet: Set<String>,
    searchSortCriteria: SearchSortCriteria,
    onQueryChanged: (String) -> Unit = {},
    onCriteriaSelected: (SearchSortCriteria) -> Unit = {},
    onClickBookmark: (Book) -> Unit = {},
    onClickCard: (Book) -> Unit = {}
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
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                singleLine = true
            )

            // 정렬 기준
            SortCriteriaSelector(
                selectedCriteria = searchSortCriteria,
                sortCriteriaList = SearchSortCriteria.getSearchCriteria(),
                onCriteriaSelected = onCriteriaSelected,
            )

            SearchStateView(
                bookList = bookList,
                bookmarkedIsbnSet = bookmarkedIsbnSet,
                query = query,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                onClickBookmark = onClickBookmark,
                onClickCard = onClickCard
            )
        }
    }
}