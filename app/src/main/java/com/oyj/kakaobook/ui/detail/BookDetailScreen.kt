package com.oyj.kakaobook.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oyj.domain.entity.Book
import com.oyj.kakaobook.data.BookItemDetail
import com.oyj.kakaobook.ui.component.ComponentConstants.Padding
import com.oyj.kakaobook.ui.detail.component.BookInfoSection
import com.oyj.kakaobook.ui.detail.component.DetailTopBar

@Composable
fun BookDetailScreen(
    navController: NavHostController,
    book: Book,
    modifier: Modifier = Modifier,
    viewModel: BookDetailViewModel = hiltViewModel(),
) {
    val bookDetail by viewModel.bookItemDetail.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.setBook(book)
        viewModel.updateBookItemDetail()
    }
    BookDetailScreen(
        modifier = modifier,
        book = bookDetail,
        onClickBack = {
            navController.popBackStack()
        },
        onClickBookmark = {
            viewModel.updateBookmark()
        }
    )
}

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    book: BookItemDetail,
    onClickBack: () -> Unit,
    onClickBookmark: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .padding(
                horizontal = Padding.LARGE,
                vertical = Padding.SMALL
            ),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Top Bar
            DetailTopBar(
                onClickBack = onClickBack,
                onClickBookmark = onClickBookmark,
                isBookmarked = book.isBookmark
            )

            // Title
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Book Info
            BookInfoSection(
                book = book
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Contents Section
            Column {
                Text("책 소개", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
                        .padding(10.dp)
                ) {
                    Text(book.contents, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Preview
@Composable
private fun BookDetailScreenPreview() {
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
        contents = "JetBrains에서 개발한 코틀린 언어를 다루는 완벽한 가이드북입니다. 코틀린의 핵심 개념부터 실무에서 활용할 수 있는 고급 기법까지 체계적으로 설명합니다. 자바와의 상호운용성, 함수형 프로그래밍, 코루틴 등 코틀린의 강력한 기능들을 예제와 함께 학습할 수 있습니다.",
        isBookmark = true
    )

    BookDetailScreen(
        book = sampleBook,
        onClickBack = { /* Preview - no action */ },
        onClickBookmark = { /* Preview - no action */ }
    )
}