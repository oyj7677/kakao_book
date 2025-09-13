package com.oyj.kakaobook.model

sealed class SearchSortCriteria(val value: String, val displayName: String) {
    object Accuracy : SearchSortCriteria("accuracy", "정확도")
    object Latest : SearchSortCriteria("latest", "최신순")

    companion object {
        fun getSearchCriteria(): List<SearchSortCriteria> = listOf(
            Accuracy,
            Latest
        )

    }
}