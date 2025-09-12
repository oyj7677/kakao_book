package com.oyj.kakaobook.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oyj.kakaobook.R
import com.oyj.kakaobook.model.BookItem
import kotlin.String

@Composable
fun BookInfoColumn(
    book: BookItem,
    modifier: Modifier = Modifier,
    onClickBookmark: (String) -> Unit = {}
) {
    Log.d("TAG", "BookInfoColumn: $book ")
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // 도서 정보 표시 컴포넌트 사용
        BookInfo(book = book)

        // 즐겨찾기 하트 이미지 (오른쪽 상단)
        Image(
            painter = painterResource(
                if (book.isBookmark) {
                    android.R.drawable.btn_star_big_on
                } else {
                    android.R.drawable.btn_star_big_off
                }
            ),
            contentDescription = stringResource(R.string.description_bookmark_image),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
                .clickable { onClickBookmark(book.isbn) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookInfoColumnPreview() {
    val book = BookItem(
        isbn = "1234567890",
        title = "Sample Book Title",
        category = "Sample Category",
        publisher = "Sample Publisher",
        authors = listOf("Author1", "Author2"),
        price = 15000,
        thumbnail = "",
        dateTime = "2014-11-17T00:00:00.000+09:00",
        isBookmark = true
    )
    BookInfoColumn(
        book = book,
    )
}