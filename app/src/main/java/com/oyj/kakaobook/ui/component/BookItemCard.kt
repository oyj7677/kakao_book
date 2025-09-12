package com.oyj.kakaobook.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oyj.kakaobook.model.BookItem

@Composable
fun BookItemCard(
    book: BookItem,
    modifier: Modifier = Modifier,
    onClickBookmark: (String) -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 도서 썸네일
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "책 표지",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                error = painterResource(android.R.drawable.ic_menu_gallery)
            )

            // 도서 정보 컬럼
            BookInfoColumn(
                book = book,
                onClickBookmark = onClickBookmark
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 180
)
@Composable
private fun BookItemCardPreview() {
    val sampleBook = BookItem(
        isbn = "1234567890",
        category = "소설",
        title = "미드나잇 라이브러리",
        publisher = "인플루엔셜",
        authors = listOf("매트 헤이그"),
        thumbnail = "",
        price = 13320,
        dateTime = "2014-11-17T00:00:00.000+09:00",
        isBookmark = true
    )

    BookItemCard(
        book = sampleBook,
        modifier = Modifier.padding(8.dp)
    )
}
