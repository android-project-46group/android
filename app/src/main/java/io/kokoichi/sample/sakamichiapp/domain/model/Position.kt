package io.kokoichi.sample.sakamichiapp.domain.model

/**
 * Data class for one position in business logic.
 */
data class Position(
    val imgUrl: String,
    val isCenter: Boolean,
    val memberName: String,
    val position: String
)