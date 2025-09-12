package com.oyj.kakaobook.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 카테고리
            Text(
                text = book.category,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // 도서 제목
            Text(
                text = book.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // 출판사
            Text(
                text = "출판사 : ${book.publisher}",
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // 저자
            Text(
                text = "저자 : ${book.authors.joinToString(", ")}",
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // 가격을 가장 아래로 밀어내기 위한 Spacer
            Spacer(modifier = Modifier.weight(1f))

            // 가격 (오른쪽 정렬)
            Text(
                text = "${book.price}원",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

        // 즐겨찾기 하트 이미지 (오른쪽 상단)
        Image(
            painter = painterResource(
                if (book.isBookmark) android.R.drawable.btn_star_big_on
                else android.R.drawable.btn_star_big_off
            ),
            contentDescription = "즐겨찾기",
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