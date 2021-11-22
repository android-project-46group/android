package io.kokoichi.sample.sakamichiapp.presentation.util

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import io.kokoichi.sample.sakamichiapp.domain.model.Blog
import io.kokoichi.sample.sakamichiapp.domain.model.Member
import io.kokoichi.sample.sakamichiapp.presentation.quiz.GroupName
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.BaseColorH
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.BaseColorN
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.BaseColorS
import io.kokoichi.sample.sakamichiapp.presentation.util.Constants.QUESTION_ENCODED
import io.kokoichi.sample.sakamichiapp.presentation.util.Constants.SLASH_ENCODED
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
            .replace("?", QUESTION_ENCODED)
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
