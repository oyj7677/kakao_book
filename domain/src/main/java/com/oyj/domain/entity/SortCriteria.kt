package com.oyj.domain.entity

sealed class SortCriteria(val value: String, val displayName: String) {
    object Accuracy : SortCriteria("accuracy", "정확도")
    object Latest : SortCriteria("latest", "최신순")

    companion object {
        fun getAllCriteria(): List<SortCriteria> = listOf(
            Accuracy,
            Latest
        )
    }
}