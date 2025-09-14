package com.oyj.kakaobook.navitation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.navigation.toRoute
import com.oyj.domain.entity.Book
import com.oyj.kakaobook.ui.bookmark.BookmarkPagingScreen
import com.oyj.kakaobook.ui.detail.BookDetailScreen
import com.oyj.kakaobook.ui.search.SearchPagingScreen

@Composable
fun NavigationGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Search.route
    ) {
        composable(Screen.Search.route) {
            SearchPagingScreen(navController = navHostController)
        }
        composable(Screen.Bookmark.route) {
            BookmarkPagingScreen(navController = navHostController)
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