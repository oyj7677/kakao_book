package com.oyj.kakaobook.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.oyj.domain.entity.Book
import com.oyj.kakaobook.mapper.PresenterMapper.toBookItem
import com.oyj.kakaobook.model.BookItem

@Composable
fun SearchResultPagingView(
    modifier: Modifier = Modifier,
    bookList: LazyPagingItems<Book>,
    bookmarkedIsbnSet: Set<String>,
    onClickBookmark: (Book) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
                }
            )
        }
    }
}

fun findBookByIsbn(bookList: LazyPagingItems<Book>, isbn: String): Book? {
    return bookList.itemSnapshotList.items.firstOrNull { it.isbn == isbn }
}