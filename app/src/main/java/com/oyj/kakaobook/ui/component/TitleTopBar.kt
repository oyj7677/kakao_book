package com.oyj.kakaobook.ui.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

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
                fontSize = 24.sp,
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