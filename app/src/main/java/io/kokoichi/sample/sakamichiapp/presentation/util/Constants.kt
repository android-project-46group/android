package io.kokoichi.sample.sakamichiapp.presentation.util


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