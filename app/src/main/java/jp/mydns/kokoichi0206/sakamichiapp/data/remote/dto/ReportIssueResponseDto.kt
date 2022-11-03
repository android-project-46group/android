package jp.mydns.kokoichi0206.sakamichiapp.data.remote.dto

import jp.mydns.kokoichi0206.model.ReportIssueResponse

data class ReportIssueResponseDto(
    val status: String
)

fun ReportIssueResponseDto.toReportIssueResponse(): jp.mydns.kokoichi0206.model.ReportIssueResponse {
    return jp.mydns.kokoichi0206.model.ReportIssueResponse(
        status = status
    )
}
