package jp.mydns.kokoichi0206.sakamichiapp.data.remote.dto

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.UpdateBlogResponse

/**
 * Data class for update blog api return.
 */
data class UpdateBlogResponseDto(
    val status: String
)

fun UpdateBlogResponseDto.toUpdateBlogResponse(): UpdateBlogResponse {
    return UpdateBlogResponse(
        status = status
    )
}
