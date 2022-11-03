package jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.other_api

import jp.mydns.kokoichi0206.sakamichiapp.common.Resource
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.dto.toReportIssueResponse
import jp.mydns.kokoichi0206.sakamichiapp.domain.repository.SakamichiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReportIssueUseCase @Inject constructor(
    private val repository: SakamichiRepository
) {
    operator fun invoke(message: String): Flow<Resource<jp.mydns.kokoichi0206.model.ReportIssueResponse>> = flow {
        try {
            emit(Resource.Loading<jp.mydns.kokoichi0206.model.ReportIssueResponse>())
            val res = repository.reportIssue(message).toReportIssueResponse()
            emit(Resource.Success<jp.mydns.kokoichi0206.model.ReportIssueResponse>(res))
        } catch (e: HttpException) {
            emit(
                Resource.Error<jp.mydns.kokoichi0206.model.ReportIssueResponse>(
                    e.localizedMessage ?: "An unexpected error occurred."
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<jp.mydns.kokoichi0206.model.ReportIssueResponse>("Couldn't reach server. Check your network connection"))
        }
    }
}
