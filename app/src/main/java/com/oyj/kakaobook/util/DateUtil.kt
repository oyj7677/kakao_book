package com.oyj.kakaobook.util

import android.util.Log


object DateUtil {
    private val YMD_REGEX = Regex("\\d{4}-\\d{2}-\\d{2}.*")
    private const val TAG = "DateUtil"
    fun formatDateToYMD(isoDateString: String): String {
        return try {
            when {
                isoDateString.isBlank() -> "날짜 정보 없음"
                isoDateString.length >= 10 && isoDateString.matches(YMD_REGEX) -> isoDateString.take(
                    10
                )
                else -> isoDateString.substringBefore("T").take(10)
            }
        } catch (e: Exception) {
            Log.e(TAG, "formatDateToYMD: ${e.stackTrace}", )
            "날짜 정보 없음"
        }
    }
}
