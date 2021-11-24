package io.kokoichi.sample.sakamichiapp.presentation.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


object Constants {

    /**
     * Escape string (percent-encoding)
     */
    const val SLASH_ENCODED = "%2F"
    const val QUESTION_ENCODED = "%3F"

    /**
     * Navigation parameter (MemberDetailScreen)
     */
    const val NAV_PARAM_MEMBER_PROPS = "memberJson"
    const val NAV_PARAM_WEBVIEW_PROPS = "url"

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
    val MAX_QUIZ_COUNT = 5
    // Padding or composable size.
    val BottomBarPadding = PaddingValues(
        top = 10.dp, bottom = 56.dp, start = 5.dp, end = 5.dp
    )

    const val BLOG_ONE_ROW_NUM = 3

    // =========== Setting Screen ===========
    const val MAX_REPORT_ISSUE_BODY_LINES = 4
}

/**
 * Get generation list according to the passed group name.
 *
 * @param group group name
 * @return List of group name (List<String>)
 */
fun getGenerationLooper(group: String): List<String> {
    return when (group) {
        "乃木坂" ->
            Constants.POSSIBLE_GENERATIONS_N
        "櫻坂" ->
            Constants.POSSIBLE_GENERATIONS_S
        "日向坂" ->
            Constants.POSSIBLE_GENERATIONS_H
        else ->
            Constants.POSSIBLE_GENERATIONS_N
    }
}