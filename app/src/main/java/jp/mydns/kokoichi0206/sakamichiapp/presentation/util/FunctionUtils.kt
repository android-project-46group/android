package jp.mydns.kokoichi0206.sakamichiapp.presentation.util

import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Blog
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Member
import jp.mydns.kokoichi0206.sakamichiapp.presentation.quiz.GroupName
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.BaseColorH
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.BaseColorN
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.BaseColorS
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Constants.QUESTION_ENCODED
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Constants.SLASH_ENCODED
import java.time.LocalTime

/**
 * Convert Member data class object to Json string.
 *
 * @param member Member data class object
 * @return Json string
 */
fun getJsonFromMember(member: Member): String {
    val props = member.copy(
        blogUrl = member.blogUrl
            .replace("/", SLASH_ENCODED)
            .replace("?", QUESTION_ENCODED),
        imgUrl = member.imgUrl
            .replace("/", SLASH_ENCODED)
            .replace("?", QUESTION_ENCODED),
        birthday = member.birthday
            .replace("/", SLASH_ENCODED),
    )
    return Gson().toJson(props)
}

/**
 * Get a proper URL as a nav parameter from Blog data class.
 *
 * @param blog Blog data class object
 * @return Encoded URL string
 */
fun getBlogUrlProps(blog: Blog): String {
    var url = blog.blogUrl
    // Add a "trading slash" for nogi member's url
    if (url.contains("nogizaka")) {
        url = "$url/"
    }
    // Return url with url encoding
    return url
        .replace("/", SLASH_ENCODED)
        .replace("?", QUESTION_ENCODED)
}

/**
 * Get baseColor of the group by group name (NOGIZAKA or 乃木坂)
 *
 * @param group Group name
 * @return BaseColor of the passed group (Color type)
 */
fun getBaseColor(group: String): Color {

    return when (group) {
        GroupName.NOGIZAKA.name, GroupName.NOGIZAKA.jname -> {
            BaseColorN
        }
        GroupName.HINATAZAKA.name, GroupName.HINATAZAKA.jname -> {
            BaseColorH
        }
        GroupName.SAKURAZAKA.name, GroupName.SAKURAZAKA.jname -> {
            BaseColorS
        }
        else -> Color.Black
    }
}

/**
 * Get subColor of the group by group name (NOGIZAKA or 乃木坂)
 *
 * @param group Group name
 * @return SubColor of the passed group (Color type)
 */
fun getSubColor(group: String): Color {

    return when (group) {
        GroupName.NOGIZAKA.name, GroupName.NOGIZAKA.jname -> {
            BaseColorN
        }
        GroupName.HINATAZAKA.name, GroupName.HINATAZAKA.jname -> {
            BaseColorH
        }
        GroupName.SAKURAZAKA.name, GroupName.SAKURAZAKA.jname -> {
            BaseColorS
        }
        else -> BaseColorN
    }
}

/**
 * Get current time + millTime
 *
 * @param time LocalTime
 * @return MillSec of current time. (34.122 second -> 34122)
 */
fun getMilliSecFromLocalTime(time: LocalTime): Int {

    return time.second * 1_000 + time.nano / 1_000_000
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
