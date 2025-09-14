package com.oyj.kakaobook.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oyj.kakaobook.R
import com.oyj.kakaobook.data.BookItemDetail
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.oyj.domain.entity.Book

private const val TAG = "BookDetailScreen"

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
    book: BookItemDetail,
    onClickBack: () -> Unit,
    onClickBookmark: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClickBack) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onClickBookmark) {
                Icon(
                    imageVector = if (book.isBookmark) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Bookmark"
                )
            }
        }

        // Title
        Text(
            text = book.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Book Info
        Row {
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
            Column(
                modifier = Modifier.align(Alignment.Top)
            ) {
                Text(
                    "저자 : ${book.author.joinToString()}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (book.translator.isNotEmpty())
                    Text(
                        "번역 : ${book.translator.joinToString()}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                Text("출판사 : ${book.publisher}", style = MaterialTheme.typography.bodyMedium)
                Text("출간일 : ${book.dateTime}", style = MaterialTheme.typography.bodyMedium)
                Text(
                    "ISBN : ${book.isbn}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text("정상가 : ${book.price}원", style = MaterialTheme.typography.bodyMedium)
                Text("할인가 : ${book.salePrice}원", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Contents Section
        Text("책 소개", style = MaterialTheme.typography.titleMedium)
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