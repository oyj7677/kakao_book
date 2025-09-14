package com.oyj.kakaobook.ui.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oyj.kakaobook.R
import com.oyj.kakaobook.data.BookItemDetail

@Composable
fun BookInfoSection(
    book: BookItemDetail,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        // Thumbnail Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.description_thumbnail_image),
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF5F5F5)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(android.R.drawable.ic_menu_gallery),
            error = painterResource(android.R.drawable.ic_menu_gallery)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Metadata
        BookMetadata(
            author = book.author,
            translator = book.translator,
            publisher = book.publisher,
            dateTime = book.dateTime,
            isbn = book.isbn,
            price = book.price,
            salePrice = book.salePrice,
            modifier = Modifier.align(Alignment.Top)
        )
    }
}

@Preview
@Composable
private fun BookInfoSectionPreview() {
    val sampleBook = BookItemDetail(
        isbn = "9788966262281",
        title = "Kotlin in Action",
        author = listOf("드미트리 제메로프", "스베틀라나 이사코바"),
        translator = listOf("오현석"),
        publisher = "에이콘출판사",
        price = 36000,
        salePrice = 32400,
        dateTime = "2017-02-28",
        thumbnail = "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1467038",
        contents = "JetBrains에서 개발한 코틀린 언어를 다루는 완벽한 가이드북입니다.",
        isBookmark = true
    )

    BookInfoSection(book = sampleBook)
}
