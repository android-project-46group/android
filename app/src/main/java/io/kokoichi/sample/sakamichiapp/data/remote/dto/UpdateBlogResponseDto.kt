package io.kokoichi.sample.sakamichiapp.data.remote.dto

import io.kokoichi.sample.sakamichiapp.domain.model.UpdateBlogResponse

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
