package jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.get_blogs

import jp.mydns.kokoichi0206.sakamichiapp.common.Resource
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.dto.toBlog
import jp.mydns.kokoichi0206.sakamichiapp.domain.repository.SakamichiRepository
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
    operator fun invoke(groupName: String): Flow<Resource<List<jp.mydns.kokoichi0206.model.Blog>>> = flow {
        try {
            emit(Resource.Loading<List<jp.mydns.kokoichi0206.model.Blog>>())
            val members = repository.getBlogs(groupName).blogs.map { it.toBlog() }
            emit(Resource.Success<List<jp.mydns.kokoichi0206.model.Blog>>(members))
        } catch (e: HttpException) {
            emit(Resource.Error<List<jp.mydns.kokoichi0206.model.Blog>>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<jp.mydns.kokoichi0206.model.Blog>>("Couldn't reach server. Check your network connection"))
        }
    }
}