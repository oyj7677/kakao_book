package com.oyj.kakaobook.navitation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.oyj.domain.entity.Book
import com.oyj.kakaobook.ui.bookmark.BookmarkScreen
import com.oyj.kakaobook.ui.detail.BookDetailScreen
import com.oyj.kakaobook.ui.search.SearchScreen

@Composable
fun NavigationGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Search.route
    ) {
        composable(Screen.Search.route) {
            SearchScreen(navController = navHostController)
        }
        composable(Screen.Bookmark.route) {
            BookmarkScreen(navController = navHostController)
        }

        composable<Book> { backStackEntry ->
            val book: Book = backStackEntry.toRoute()   // 타입 세이프하게 수신
            BookDetailScreen(
                navController = navHostController,
                book = book
            )
        }
    }
}