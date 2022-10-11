package jp.mydns.kokoichi0206.sakamichiapp.data.remote.dto

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.ReportIssueResponse

data class ReportIssueResponseDto(
    val status: String
)

fun ReportIssueResponseDto.toReportIssueResponse(): ReportIssueResponse {
    return ReportIssueResponse(
        status = status
    )
}
