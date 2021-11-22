package io.kokoichi.sample.sakamichiapp.domain.usecase.get_blogs

import io.kokoichi.sample.sakamichiapp.common.Resource
import io.kokoichi.sample.sakamichiapp.data.remote.dto.toBlog
import io.kokoichi.sample.sakamichiapp.domain.model.Blog
import io.kokoichi.sample.sakamichiapp.domain.repository.SakamichiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * UseCase of getMembers function in a repository.
 */
class GetBlogsUseCase @Inject constructor(
    private val repository: SakamichiRepository
) {
    operator fun invoke(groupName: String): Flow<Resource<List<Blog>>> = flow {
        try {
            emit(Resource.Loading<List<Blog>>())
            val members = repository.getBlogs(groupName).blogs.map { it.toBlog() }
            emit(Resource.Success<List<Blog>>(members))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Blog>>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<Blog>>("Couldn't reach server. Check your network connection"))
        }
    }
}