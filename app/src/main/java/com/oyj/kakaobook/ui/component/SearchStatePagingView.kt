package com.oyj.kakaobook.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import com.oyj.domain.entity.Book
import com.oyj.kakaobook.R
import com.oyj.kakaobook.model.Empty
import com.oyj.kakaobook.model.Error
import com.oyj.kakaobook.model.Init
import com.oyj.kakaobook.model.Loading
import com.oyj.kakaobook.model.SearchUiState
import com.oyj.kakaobook.model.Success

@Composable
fun SearchStatePagingView(
    bookList: LazyPagingItems<Book>,
    query: String,
    modifier: Modifier = Modifier,
    onClickBookmark: (String) -> Unit = {}
) {
    SearchResultPagingView(
        modifier = modifier.fillMaxSize(),
        bookList = bookList,
        onClickBookmark = onClickBookmark
    )

//    when (searchUiState) {
//        is Error -> {
//            // 에러 상태
//            EmptyState(
//                icon = Icons.Default.Search,
//                message = searchUiState.message
//            )
//        }
//
//        is Loading -> {
//            LoadingView()
//        }
//
//        is Success -> {
//            if (searchUiState.bookList.isEmpty()) {
//                // 빈 상태
//                EmptyState(
//                    icon = Icons.Default.Search,
//                    message = if (query.isEmpty()) {
//                        stringResource(R.string.text_init_result)
//                    } else {
//                        stringResource(R.string.text_no_result)
//                    }
//                )
//            } else {
//                // 책 리스트
//                SearchResultView(
//                    modifier = modifier.fillMaxSize(),
//                    bookList = searchUiState.bookList,
//                    onClickBookmark = onClickBookmark
//                )
//            }
//        }
//
//        is Init -> {
//            // 초기 상태
//            EmptyState(
//                icon = Icons.Default.Search,
//                message = stringResource(R.string.text_init_result)
//            )
//        }
//
//        is Empty -> {
//            // 검색 결과 없음 상태
//            EmptyState(
//                icon = Icons.Default.Search,
//                message = stringResource(R.string.text_no_result)
//            )
//        }
//    }
}
