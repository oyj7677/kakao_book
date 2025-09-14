package com.oyj.kakaobook.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.oyj.kakaobook.R
import com.oyj.kakaobook.data.BookItem
import com.oyj.kakaobook.ui.component.ComponentConstants.Padding
import com.oyj.kakaobook.ui.component.ComponentConstants.Text.MAX_LINES_SINGLE
import com.oyj.kakaobook.ui.component.ComponentConstants.Typography
import com.oyj.kakaobook.util.DateUtil

/**
 * 도서 상세 정보를 표시하는 컴포넌트
 * @param book 표시할 도서 정보
 * @param modifier 컴포넌트에 적용할 Modifier
 * @param showCategory 카테고리 표시 여부 (기본값: true)
 * @param showPublisher 출판사 표시 여부 (기본값: true)
 * @param showAuthors 저자 표시 여부 (기본값: true)
 * @param showPublishDate 출간일 표시 여부 (기본값: true)
 * @param showPrice 가격 표시 여부 (기본값: true)
 */
@Composable
fun BookInfo(
    book: BookItem,
    modifier: Modifier = Modifier,
    showCategory: Boolean = true,
    showPublisher: Boolean = true,
    showAuthors: Boolean = true,
    showPublishDate: Boolean = true,
    showPrice: Boolean = true
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // 카테고리
        if (showCategory) {
            Text(
                text = book.category,
                fontSize = Typography.CAPTION_SIZE,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis
            )
        }

        // 도서 제목
        Text(
            text = book.title,
            fontSize = Typography.TITLE_SIZE,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = Padding.EXTRA_SMALL)
        )

        // 출판사
        if (showPublisher) {
            Text(
                text = stringResource(
                    id = R.string.text_publisher_info,
                    book.publisher
                ),
                fontSize = Typography.CAPTION_SIZE,
                color = Color.Gray,
                maxLines = MAX_LINES_SINGLE,
                overflow = TextOverflow.Ellipsis
            )
        }

        // 저자
        if (showAuthors) {
            Text(
                text = stringResource(
                    id = R.string.text_authors_info,
                    book.authors.joinToString(", ")
                ),
                fontSize = Typography.CAPTION_SIZE,
                color = Color.Gray,
                maxLines = MAX_LINES_SINGLE,
                overflow = TextOverflow.Ellipsis
            )
        }

        // 출간일
        if (showPublishDate) {
            Text(
                text = stringResource(
                    id = R.string.text_date_time_info,
                    DateUtil.formatDateToYMD(book.dateTime)
                ),
                fontSize = Typography.CAPTION_SIZE,
                color = Color.Gray,
                maxLines = MAX_LINES_SINGLE,
                overflow = TextOverflow.Ellipsis
            )
        }

        // 가격
        if (showPrice) {
            Text(
                text = stringResource(
                    id = R.string.text_price_info,
                    book.price
                ),
                fontSize = Typography.BODY_SIZE,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                maxLines = MAX_LINES_SINGLE,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsColumnPreview() {
    val book = BookItem(
        isbn = "1234567890",
        title = "안드로이드 앱 개발의 모든 것: Kotlin과 Jetpack Compose로 배우는 현대적인 안드로이드 개발",
        category = "컴퓨터/IT",
        publisher = "한빛미디어",
        authors = listOf("김안드로이드", "박코틀린"),
        price = 28000,
        thumbnail = "",
        dateTime = "2024-01-15T00:00:00.000+09:00",
        isBookmark = false
    )

    BookInfo(book = book)
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsColumnMinimalPreview() {
    val book = BookItem(
        isbn = "1234567890",
        title = "짧은 제목",
        category = "소설",
        publisher = "출판사",
        authors = listOf("저자명"),
        price = 15000,
        thumbnail = "",
        dateTime = "2023-12-01T00:00:00.000+09:00",
        isBookmark = false
    )

    // 제목과 가격만 표시하는 예시
    BookInfo(
        book = book,
        showCategory = false,
        showPublisher = false,
        showAuthors = false,
        showPublishDate = false
    )
}
