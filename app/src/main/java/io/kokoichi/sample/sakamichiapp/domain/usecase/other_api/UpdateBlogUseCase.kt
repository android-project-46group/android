package io.kokoichi.sample.sakamichiapp.domain.usecase.other_api

import io.kokoichi.sample.sakamichiapp.common.Resource
import io.kokoichi.sample.sakamichiapp.data.remote.dto.toUpdateBlogResponse
import io.kokoichi.sample.sakamichiapp.domain.model.UpdateBlogResponse
import io.kokoichi.sample.sakamichiapp.domain.repository.SakamichiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateBlogUseCase @Inject constructor(
    private val repository: SakamichiRepository
) {
    operator fun invoke(): Flow<Resource<UpdateBlogResponse>> = flow {
        try {
            emit(Resource.Loading<UpdateBlogResponse>())
            val res = repository.updateBlog().toUpdateBlogResponse()
            emit(Resource.Success<UpdateBlogResponse>(res))
        } catch (e: HttpException) {
            emit(Resource.Error<UpdateBlogResponse>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<UpdateBlogResponse>("Couldn't reach server. Check your network connection"))
        }
    }
}
