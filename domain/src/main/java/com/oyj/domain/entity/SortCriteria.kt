package com.oyj.domain.entity

sealed class SortCriteria(val value: String) {
    object Accuracy : SortCriteria("accuracy")
    object Recency : SortCriteria("recency")

    companion object {
        fun getAllCriteria(): List<SortCriteria> = listOf(
            Accuracy,
            Recency
        )
    }
}