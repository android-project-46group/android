package jp.mydns.kokoichi0206.data.remote.dto

import com.google.gson.annotations.SerializedName

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