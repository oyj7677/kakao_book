package com.oyj.kakaobook.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oyj.kakaobook.model.BookItem
import com.oyj.kakaobook.model.Empty
import com.oyj.kakaobook.model.Error
import com.oyj.kakaobook.model.Init
import com.oyj.kakaobook.model.Loading
import com.oyj.kakaobook.model.SearchUiState
import com.oyj.kakaobook.model.Success

@Composable
fun SearchStateView(
    searchUiState: SearchUiState,
    query: String,
    modifier: Modifier = Modifier,
    onClickBookmark: (String) -> Unit = {}
) {
    when (searchUiState) {
        is Error -> {
            // 에러 상태
            EmptyState(
                icon = Icons.Default.Search,
                message = searchUiState.message
            )
        }

        is Loading -> {
            LoadingView()
        }

        is Success -> {
            if (searchUiState.bookList.isEmpty()) {
                // 빈 상태
                EmptyState(
                    icon = Icons.Default.Search,
                    message = if (query.isEmpty()) "검색어를 입력해주세요" else "검색 결과가 없습니다"
                )
            } else {
                // 책 리스트
                SearchResultView(
                    modifier = modifier.fillMaxSize(),
                    bookList = searchUiState.bookList,
                    onClickBookmark = onClickBookmark
                )
            }
        }
        is Init -> {
            // 초기 상태
            EmptyState(
                icon = Icons.Default.Search,
                message = "검색어를 입력해주세요"
            )
        }
        is Empty -> {
            // 검색 결과 없음 상태
            EmptyState(
                icon = Icons.Default.Search,
                message = "검색 결과가 없습니다"
            )
        }
    }
}
