package io.kokoichi.sample.sakamichiapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import io.kokoichi.sample.sakamichiapp.domain.model.Position

/**
 * Data class for one position in the API.
 */
data class PositionDto(
    @SerializedName("img_url")
    val imgUrl: String,
    @SerializedName("is_center")
    val isCenter: Boolean,
    @SerializedName("member_name")
    val memberName: String,
    @SerializedName("position")
    val position: String
)

fun PositionDto.toPosition(): Position {
    return Position(
        imgUrl = imgUrl,
        isCenter = isCenter,
        memberName = memberName,
        position = position
    )
}