package com.oyj.kakaobook.ui.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BookMetadata(
    author: List<String>,
    translator: List<String>,
    publisher: String,
    dateTime: String,
    isbn: String,
    price: Int,
    salePrice: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "저자 : ${author.joinToString()}",
            style = MaterialTheme.typography.bodyMedium
        )
        if (translator.isNotEmpty()) {
            Text(
                "번역 : ${translator.joinToString()}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text("출판사 : $publisher", style = MaterialTheme.typography.bodyMedium)
        Text("출간일 : $dateTime", style = MaterialTheme.typography.bodyMedium)
        Text(
            "ISBN : $isbn",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text("정상가 : ${price}원", style = MaterialTheme.typography.bodyMedium)
        Text("할인가 : ${salePrice}원", style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview
@Composable
private fun BookMetadataPreview() {
    BookMetadata(
        author = listOf("드미트리 제메로프", "스베틀라나 이사코바"),
        translator = listOf("오현석"),
        publisher = "에이콘출판사",
        dateTime = "2017-02-28",
        isbn = "9788966262281",
        price = 36000,
        salePrice = 32400
    )
}
