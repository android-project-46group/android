package jp.mydns.kokoichi0206.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data class for positions API.
 */
data class PositionsDto(
    @SerializedName("positions")
    val positions: List<PositionDto>
)