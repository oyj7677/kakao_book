package com.oyj.kakaobook.ui.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.oyj.kakaobook.ui.component.ComponentConstants.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTopBar(
    modifier: Modifier = Modifier,
    title: String,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = Typography.TITLE_LARGE_SIZE,
                fontWeight = FontWeight.Bold
            )
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun TitleTopBarPreview() {
    TitleTopBar(
        title = "책 리스트"
    )
}