package com.oyj.kakaobook.ui.detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oyj.kakaobook.R
import com.oyj.kakaobook.data.BookItemDetail
import com.oyj.kakaobook.ui.component.ComponentConstants.Card
import com.oyj.kakaobook.ui.component.ComponentConstants.Padding
import com.oyj.kakaobook.ui.component.ComponentConstants.Size

@Composable
fun BookInfoSection(
    book: BookItemDetail,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top
    ) {
        // 썸네일 이미지
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.description_thumbnail_image),
            modifier = Modifier
                .clip(RoundedCornerShape(Card.CORNER_RADIUS_SMALL))
                .width(Size.THUMBNAIL_WIDTH)
                .height(Size.THUMBNAIL_HEIGHT),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            placeholder = painterResource(android.R.drawable.ic_menu_gallery),
            error = painterResource(android.R.drawable.ic_menu_gallery)
        )

        Spacer(modifier = Modifier.width(Padding.LARGE))

        // Metadata
        BookMetadata(
            author = book.author,
            translator = book.translator,
            publisher = book.publisher,
            dateTime = book.dateTime,
            isbn = book.isbn,
            price = book.price,
            salePrice = book.salePrice,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}

@Preview(showBackground = true)
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
