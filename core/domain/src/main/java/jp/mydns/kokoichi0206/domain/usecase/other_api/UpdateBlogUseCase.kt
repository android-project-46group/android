package jp.mydns.kokoichi0206.domain.usecase.other_api

import com.squareup.moshi.JsonDataException
import jp.mydns.kokoichi0206.common.Resource
import jp.mydns.kokoichi0206.data.remote.dto.toUpdateBlogResponse
import jp.mydns.kokoichi0206.data.repository.SakamichiRepository
import jp.mydns.kokoichi0206.model.UpdateBlogResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateBlogUseCase @Inject constructor(
    private val repository: SakamichiRepository,
) {
    operator fun invoke(): Flow<Resource<UpdateBlogResponse>> = flow {
        try {
            emit(Resource.Loading<jp.mydns.kokoichi0206.model.UpdateBlogResponse>())
            val res = repository.updateBlog().toUpdateBlogResponse()
            emit(Resource.Success<jp.mydns.kokoichi0206.model.UpdateBlogResponse>(res))
        } catch (e: HttpException) {
            emit(Resource.Error<jp.mydns.kokoichi0206.model.UpdateBlogResponse>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<jp.mydns.kokoichi0206.model.UpdateBlogResponse>("Couldn't reach server. Check your network connection"))
        } catch (e: JsonDataException) {
            emit(Resource.Error("Something unexpected happened at server.\nPlease report to us."))
        }
    }
}
