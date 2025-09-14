package com.oyj.kakaobook.navitation

import com.oyj.domain.entity.Book

sealed class Screen(val route: String) {
    // 하단 네비게이션 화면들
    data object Search : Screen("search")
    data object Bookmark : Screen("bookmark")

    // 상세 화면 (하단 네비게이션에 표시되지 않음)
    data object BookDetail : Screen("book/{book}") {
        fun createRoute(book: Book): String = "book/$book"
    }
}
