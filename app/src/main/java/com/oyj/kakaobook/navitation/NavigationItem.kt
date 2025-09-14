package com.oyj.kakaobook.navitation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    data object Search : NavigationItem(Screen.Search.route, "검색", Icons.Filled.Search)
    data object Bookmark : NavigationItem(Screen.Bookmark.route, "북마크", Icons.Filled.Favorite)
}
