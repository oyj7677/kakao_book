package com.oyj.kakaobook.data

sealed class SortOrder(val value: String, val displayName: String) {
    object Ascending : SortOrder("ASC", "오름차순")
    object Descending : SortOrder("DESC", "내림차순")
}