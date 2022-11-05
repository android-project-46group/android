package jp.mydns.kokoichi0206.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object Constants {

    /**
     * Base url for WebAPI
     */
    const val BASE_URL = "https://kokoichi0206.mydns.jp/"

    // =========== Compose Navigation ===========
    /**
     * Escape string (percent-encoding)
     */
    const val SLASH_ENCODED = "%2F"
    const val QUESTION_ENCODED = "%3F"

    // =========== MemberList Screen ===========
    val BLOOD_TYPE_LIST = listOf<String>(
        "A型", "B型", "O型", "AB型", "不明",
    )

    val POSSIBLE_GENERATIONS_N = listOf<String>(
        "1期生", "2期生", "3期生", "4期生",
    )
    val POSSIBLE_GENERATIONS_S = listOf<String>(
        "1期生", "2期生",
    )
    val POSSIBLE_GENERATIONS_H = listOf<String>(
        "1期生", "2期生", "3期生",
    )

    // =========== Quiz Screen ===========
    // Colors to display QuizTypes.
    val QuizTypeColors = listOf(
        Color(0.101960786F, 0.0F, 0.8235294F, 1.0F),
        Color(0.83137256F, 0.16470589F, 0.101960786F, 1.0F),
        Color(0.9607843F, 0.5137255F, 0.9607843F, 1.0F),
        Color(0.13333334F, 0.4F, 0.25882354F, 1.0F),
    )

    // =========== Blog Screen ===========
    const val MAX_QUIZ_COUNT = 5

    const val BLOG_ONE_ROW_NUM = 3

    // =========== Setting Screen ===========
    const val MAX_REPORT_ISSUE_BODY_LINES = 4

    const val NAVIGATION_DURATION_MILLIS = 600

    // version in settings
    const val NEED_TAP_NUM_TO_SHOW_SNACK_BAR = 2
    const val NEED_TAP_NUM_TO_BE_DEVELOPER = 7
    const val CANCELLATION_THRESHOLD_MILLI_TIMES_OF_DEVELOPER = 1_000
}
