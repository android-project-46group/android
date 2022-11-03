package jp.mydns.kokoichi0206.data.remote.dto

import jp.mydns.kokoichi0206.model.UpdateBlogResponse

/**
 * Data class for update blog api return.
 */
data class UpdateBlogResponseDto(
    val status: String
)

fun UpdateBlogResponseDto.toUpdateBlogResponse(): jp.mydns.kokoichi0206.model.UpdateBlogResponse {
    return jp.mydns.kokoichi0206.model.UpdateBlogResponse(
        status = status
    )
}
