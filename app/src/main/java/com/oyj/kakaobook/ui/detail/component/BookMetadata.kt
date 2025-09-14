package com.oyj.kakaobook.ui.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oyj.kakaobook.R
import com.oyj.kakaobook.util.DateUtil

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
    Column(
        modifier = modifier
    ) {
        Text(
            "저자 : ${author.joinToString()}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            "번역 : ${translator.joinToString()}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(R.string.text_publisher_info, publisher),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(R.string.text_date_time_info, DateUtil.formatDateToYMD(dateTime)),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(R.string.text_isbn, isbn),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(R.string.text_price_info, price),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(R.string.text_sale_price_info, salePrice),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
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
