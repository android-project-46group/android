package io.kokoichi.sample.sakamichiapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import io.kokoichi.sample.sakamichiapp.domain.model.Member

/**
 * Data class for one member in the API.
 */
data class MemberDto(
    val birthday: String,
    @SerializedName("blog_url")
    val blogUrl: String,
    @SerializedName("blood_type")
    val bloodType: String,
    val generation: String,
    val height: String,
    @SerializedName("img_url")
    val imgUrl: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_name")
    val nameName: String
)

fun MemberDto.toMember(): Member {
    return Member(
        blogUrl = blogUrl,
        bloodType = bloodType,
        generation = generation,
        height = height,
        imgUrl = imgUrl,
        name = nameName,
        birthday = birthday
    )
}