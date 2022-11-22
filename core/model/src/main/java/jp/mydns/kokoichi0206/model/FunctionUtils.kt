package jp.mydns.kokoichi0206.model

import com.squareup.moshi.Moshi
import jp.mydns.kokoichi0206.common.Constants

/**
 * Convert Member data class object to Json string.
 *
 * @param member Member data class object
 * @return Json string
 */
fun getJsonFromMember(member: Member): String {
    val props = member.copy(
        blogUrl = member.blogUrl
            .replace("/", Constants.SLASH_ENCODED)
            .replace("?", Constants.QUESTION_ENCODED),
        imgUrl = member.imgUrl
            .replace("/", Constants.SLASH_ENCODED)
            .replace("?", Constants.QUESTION_ENCODED),
        birthday = member.birthday
            .replace("/", Constants.SLASH_ENCODED),
    )
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter(Member::class.java)
    return jsonAdapter.toJson(props)
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
