package com.oyj.kakaobook.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oyj.kakaobook.data.BookItem
import com.oyj.kakaobook.ui.component.ComponentConstants.Padding

@Composable
fun SearchResultView(
    modifier: Modifier = Modifier,
    bookList: List<BookItem>,
    onClickBookmark: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(Padding.LARGE),
        verticalArrangement = Arrangement.spacedBy(Padding.MEDIUM)
    ) {
        items(bookList) { book ->
            BookItemCard(
                book = book,
                onClickBookmark = onClickBookmark
            )
        }
    }
}