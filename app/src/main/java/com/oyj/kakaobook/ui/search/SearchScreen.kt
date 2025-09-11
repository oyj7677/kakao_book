package com.oyj.kakaobook.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oyj.kakaobook.model.BookItem
import com.oyj.kakaobook.model.SortCriteria
import com.oyj.kakaobook.ui.component.BookItemCard
import com.oyj.kakaobook.ui.component.EmptyState
import com.oyj.kakaobook.ui.component.SortCriteriaSelector
import com.oyj.kakaobook.ui.component.TitleTopBar

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel()
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val bookList by viewModel.bookList.collectAsStateWithLifecycle()

    SearchScreen(
        modifier = modifier,
        query = query,
        bookList = bookList,
        onQueryChanged = viewModel::setQuery
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    query: String,
    bookList: List<BookItem>,
    onQueryChanged: (String) -> Unit = {},
) {
    var selectedCriteria by remember { mutableStateOf(SortCriteria.Accuracy) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TitleTopBar(title = "검색")
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
                placeholder = { Text("책 제목을 검색해보세요") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "검색"
                    )
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // 정렬 기준
            SortCriteriaSelector(
                selectedCriteria = selectedCriteria,
                onCriteriaSelected = { criteria ->
//                    selectedCriteria = criteria
                    // TODO: 정렬 로직 연결
                }
            )

            // 리스트
            if (bookList.isEmpty()) {
                // 빈 상태
                EmptyState(
                    icon = Icons.Default.Search,
                    message = if (query.isEmpty()) "검색어를 입력해주세요" else "검색 결과가 없습니다"
                )
            } else {
                // 책 리스트
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(bookList) { book ->
                        BookItemCard(book = book)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        query = "test",
        bookList = listOf()
    )
}


@Preview
@Composable
private fun SearchScreenWithBooksPreview() {
    val sampleBooks = listOf(
        BookItem(
            category = "소설",
            title = "미드나잇 라이브러리",
            publisher = "인플루엔셜",
            authors = listOf("매트 헤이그"),
            thumbnail = "",
            price = 13320,
            isBookmark = false
        ),
        BookItem(
            category = "에세이",
            title = "아몬드",
            publisher = "창비",
            authors = listOf("손원평"),
            thumbnail = "",
            price = 12600,
            isBookmark = true
        ),
        BookItem(
            category = "자기계발",
            title = "원씽 The One Thing",
            publisher = "비즈니스북스",
            authors = listOf("게리 켈러", "제이 파파산"),
            thumbnail = "",
            price = 14400,
            isBookmark = false
        )
    )

    SearchScreen(
        query = "미드나잇",
        bookList = sampleBooks
    )
}
