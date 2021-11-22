package io.kokoichi.sample.sakamichiapp.domain.usecase.other_api

import io.kokoichi.sample.sakamichiapp.common.Resource
import io.kokoichi.sample.sakamichiapp.data.remote.dto.toReportIssueResponse
import io.kokoichi.sample.sakamichiapp.domain.model.ReportIssueResponse
import io.kokoichi.sample.sakamichiapp.domain.repository.SakamichiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReportIssueUseCase @Inject constructor(
    private val repository: SakamichiRepository
) {
    operator fun invoke(message: String): Flow<Resource<ReportIssueResponse>> = flow {
        try {
            emit(Resource.Loading<ReportIssueResponse>())
            val res = repository.reportIssue(message).toReportIssueResponse()
            emit(Resource.Success<ReportIssueResponse>(res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<ReportIssueResponse>(
                    e.localizedMessage ?: "An unexpected error occurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<ReportIssueResponse>("Couldn't reach server. Check your network connection"))
        }
    }
}
