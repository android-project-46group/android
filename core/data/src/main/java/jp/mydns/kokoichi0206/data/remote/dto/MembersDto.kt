package jp.mydns.kokoichi0206.data.remote.dto

/**
 * Data class for members API.
 */
data class MembersDto(
    val counts: Int,
    val members: List<MemberDto>,
)