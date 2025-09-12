package com.oyj.kakaobook.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtil {

    /**
     * ISO 8601 형식의 날짜 문자열을 yyyy-mm-dd 형식으로 변환
     * @param isoDateString ISO 8601 형식 날짜 (예: "2014-11-17T00:00:00.000+09:00")
     * @return yyyy-mm-dd 형식 날짜 (예: "2014-11-17") 또는 변환 실패 시 원본 문자열의 앞 10자리
     */
    fun formatDateToYMD(isoDateString: String): String {
        return try {
            // 입력 형식이 이미 yyyy-mm-dd인 경우 (10자리)
            if (isoDateString.length >= 10 && isoDateString.matches(Regex("\\d{4}-\\d{2}-\\d{2}.*"))) {
                isoDateString.take(10)
            } else {
                // 빈 문자열이거나 null인 경우
                if (isoDateString.isBlank()) {
                    "날짜 정보 없음"
                } else {
                    // 안전하게 앞 10자리만 추출 (T 앞부분)
                    isoDateString.substringBefore("T").take(10)
                }
            }
        } catch (e: Exception) {
            // 예외 발생 시 안전한 기본값 반환
            "날짜 형식 오류"
        }
    }
}
