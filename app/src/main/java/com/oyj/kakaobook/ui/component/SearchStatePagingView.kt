package com.oyj.kakaobook.ui.component

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.oyj.domain.entity.Book
import com.oyj.kakaobook.R

private const val TAG = "SearchStatePagingView"

@Composable
fun SearchStatePagingView(
    bookList: LazyPagingItems<Book>,
    bookmarkedIsbnSet: Set<String>,
    query: String,
    modifier: Modifier = Modifier,
    onClickBookmark: (Book) -> Unit = {},
    onClickCard: (Book) -> Unit = {}
) {
    bookList.apply {
        when {
            loadState.refresh == LoadState.Loading -> {
                LoadingView()
            }

            loadState.refresh is LoadState.Error -> {
                val e = bookList.loadState.refresh as LoadState.Error
                Log.e(TAG, "SearchStatePagingView: ${e.error.stackTrace}", )
                EmptyState(
                    icon = Icons.Default.Search,
                    message = stringResource(R.string.error_search)
                )
            }

            bookList.itemCount == 0 -> {
                // 검색 결과 없음 상태
                EmptyState(
                    icon = Icons.Default.Search,
                    message = if (query.isEmpty()) {
                        stringResource(R.string.text_init_result)
                    } else {
                        stringResource(R.string.text_no_result)
                    }
                )
            }

            else -> {
                SearchResultPagingView(
                    modifier = modifier,
                    bookList = bookList,
                    bookmarkedIsbnSet = bookmarkedIsbnSet,
                    onClickBookmark = onClickBookmark,
                    onClickCard = onClickCard
                )
            }
        }
    }
}
