package jp.mydns.kokoichi0206.domain.usecase.get_blogs

import com.squareup.moshi.JsonDataException
import jp.mydns.kokoichi0206.common.Resource
import jp.mydns.kokoichi0206.data.remote.dto.toBlog
import jp.mydns.kokoichi0206.data.repository.SakamichiRepository
import jp.mydns.kokoichi0206.model.Blog
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
    operator fun invoke(groupName: String): Flow<jp.mydns.kokoichi0206.common.Resource<List<Blog>>> = flow {
        try {
            emit(Resource.Loading())
            val members = repository.getBlogs(groupName).blogs.map { it.toBlog() }
            emit(Resource.Success(members))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your network connection"))
        } catch (e: JsonDataException) {
            emit(Resource.Error("Something unexpected happened at server.\nPlease report to us."))
        }
    }
}