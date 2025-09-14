package com.oyj.kakaobook.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.oyj.domain.entity.Book
import com.oyj.kakaobook.mapper.PresenterMapper.toBookItem
import com.oyj.kakaobook.ui.component.ComponentConstants.LazyColumn.BOTTOM_PADDING
import com.oyj.kakaobook.ui.component.ComponentConstants.LazyColumn.VERTICAL_SPACING

@Composable
fun SearchResultPagingView(
    modifier: Modifier = Modifier,
    bookList: LazyPagingItems<Book>,
    bookmarkedIsbnSet: Set<String>,
    onClickBookmark: (Book) -> Unit = {},
    onClickCard: (Book) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = BOTTOM_PADDING),
        verticalArrangement = Arrangement.spacedBy(VERTICAL_SPACING)
    ) {
        items(
            count = bookList.itemCount,
            key = { index -> bookList[index]?.isbn ?: index }
        ) { index ->
            val isBookmark = bookmarkedIsbnSet.contains(bookList[index]?.isbn)
            val bookItem = bookList[index]?.toBookItem(isBookmark) ?: return@items
            BookItemCard(
                book = bookItem,
                onClickBookmark = {
                    val book = findBookByIsbn(bookList, bookItem.isbn) ?: return@BookItemCard
                    onClickBookmark(book)
                },
                onClickCard = {
                    val book = findBookByIsbn(bookList, bookItem.isbn) ?: return@BookItemCard
                    onClickCard(book)
                }
            )
        }
    }
}

fun findBookByIsbn(bookList: LazyPagingItems<Book>, isbn: String): Book? {
    return bookList.itemSnapshotList.items.firstOrNull { it.isbn == isbn }
}
