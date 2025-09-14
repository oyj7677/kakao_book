package com.oyj.kakaobook.ui.component


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object ComponentConstants {
    /**
     * Component 패키지에서 사용하는 공통 상수들
     */
// Padding & Spacing
    object Padding {
        val EXTRA_SMALL = 4.dp
        val SMALL = 8.dp
        val MEDIUM = 12.dp
        val LARGE = 16.dp
    }

    // Card Properties
    object Card {
        val ELEVATION = 4.dp
        val CORNER_RADIUS = 12.dp
        val CORNER_RADIUS_SMALL = 8.dp
    }

    // Typography
    object Typography {
        val TITLE_LARGE_SIZE = 24.sp
        val CAPTION_SIZE = 12.sp
        val BODY_SIZE = 14.sp
        val TITLE_SIZE = 16.sp
    }


    // Icon & Image
    object Size {
        val ICON_MEDIUM = 24.dp
        val ICON_LARGE = 64.dp
        val TOP_BAR_HEIGHT = 56.dp
        val THUMBNAIL_WIDTH = 120.dp
        val THUMBNAIL_HEIGHT = 174.dp
    }

    // Text Properties
    object Text {
        const val MAX_LINES_SINGLE = 1
        const val MAX_LINES_DOUBLE = 2
    }

}