package io.kokoichi.sample.sakamichiapp.presentation.util

import com.google.gson.Gson
import io.kokoichi.sample.sakamichiapp.domain.model.Blog
import io.kokoichi.sample.sakamichiapp.domain.model.Member
import io.kokoichi.sample.sakamichiapp.presentation.util.Constants.QUESTION_ENCODED
import io.kokoichi.sample.sakamichiapp.presentation.util.Constants.SLASH_ENCODED

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
        .replace("/", Constants.SLASH_ENCODED)
        .replace("?", Constants.QUESTION_ENCODED)
}
