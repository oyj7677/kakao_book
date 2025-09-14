package com.oyj.kakaobook.data

sealed class SearchSortCriteria(val value: String, val displayName: String) {
    object Accuracy : SearchSortCriteria("accuracy", "정확도")
    object Latest : SearchSortCriteria("latest", "최신순")
    object Title : SearchSortCriteria("Title", "제목순")
    object Price : SearchSortCriteria("Price", "가격순")
    companion object {
        fun getSearchCriteria(): List<SearchSortCriteria> = listOf(
            Accuracy,
            Latest
        )

        fun getBookmarkCriteria(): List<SearchSortCriteria> = listOf(
            Title,
            Price,
        )
    }
}