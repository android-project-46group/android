package jp.mydns.kokoichi0206.data.remote.dto

import com.squareup.moshi.Json

/**
 * Data class for one member in the API.
 */
data class MemberDto(
    val birthday: String,
    @field:Json(name = "blog_url")
    val blogUrl: String,
    @field:Json(name = "blood_type")
    val bloodType: String,
    val generation: String,
    val height: String,
    @field:Json(name = "img_url")
    val imgUrl: String,
    @field:Json(name = "id")
    val userId: Int,
    @field:Json(name = "name")
    val nameName: String
)

fun MemberDto.toMember(): jp.mydns.kokoichi0206.model.Member {
    return jp.mydns.kokoichi0206.model.Member(
        blogUrl = blogUrl,
        bloodType = bloodType,
        generation = generation,
        height = height,
        imgUrl = imgUrl,
        name = nameName,
        birthday = birthday
    )
}