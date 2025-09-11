package com.oyj.kakaobook.model

sealed class SortCriteria(
    val displayName: String
) {
    data object Accuracy : SortCriteria("정확도순")
    data object Latest : SortCriteria("최신순")
    data object Price : SortCriteria("가격순")

    companion object {
        fun getAllCriteria(): List<SortCriteria> = listOf(
            Accuracy,
            Latest,
            Price
        )
    }
}
