package com.oyj.kakaobook.ui.detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oyj.kakaobook.ui.component.ComponentConstants.Size

@Composable
fun DetailTopBar(
    isBookmarked: Boolean,
    onClickBack: () -> Unit,
    onClickBookmark: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(Size.TOP_BAR_HEIGHT),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClickBack) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onClickBookmark) {
            Icon(
                imageVector = if (isBookmarked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Bookmark"
            )
        }
    }
}

@Preview
@Composable
private fun DetailTopBarPreview() {
    DetailTopBar(
        isBookmarked = true,
        onClickBack = { },
        onClickBookmark = { }
    )
}
