package jp.mydns.kokoichi0206.data.remote.dto

import com.squareup.moshi.Json

/**
 * Data class for one position in the API.
 */
data class PositionDto(
    @field:Json(name = "img_url")
    val imgUrl: String,
    @field:Json(name = "is_center")
    val isCenter: Boolean,
    @field:Json(name = "member_name")
    val memberName: String,
    @field:Json(name = "position")
    val position: String
)

fun PositionDto.toPosition(): jp.mydns.kokoichi0206.model.Position {
    return jp.mydns.kokoichi0206.model.Position(
        imgUrl = imgUrl,
        isCenter = isCenter,
        memberName = memberName,
        position = position
    )
}