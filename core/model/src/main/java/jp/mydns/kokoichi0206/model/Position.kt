package jp.mydns.kokoichi0206.model

/**
 * Data class for one position in business logic.
 */
data class Position(
    val imgUrl: String,
    val isCenter: Boolean,
    val memberName: String,
    val position: String
)