package io.kokoichi.sample.sakamichiapp.presentation.util

import com.google.gson.Gson
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
