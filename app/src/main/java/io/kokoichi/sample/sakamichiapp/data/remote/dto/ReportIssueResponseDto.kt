package io.kokoichi.sample.sakamichiapp.data.remote.dto

import io.kokoichi.sample.sakamichiapp.domain.model.ReportIssueResponse

data class ReportIssueResponseDto(
    val status: String
)

fun ReportIssueResponseDto.toReportIssueResponse(): ReportIssueResponse {
    return ReportIssueResponse(
        status = status
    )
}
