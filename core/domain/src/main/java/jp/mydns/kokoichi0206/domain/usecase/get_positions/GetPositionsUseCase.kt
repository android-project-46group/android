package jp.mydns.kokoichi0206.domain.usecase.get_positions

import com.squareup.moshi.JsonDataException
import jp.mydns.kokoichi0206.common.Resource
import jp.mydns.kokoichi0206.data.remote.dto.toPosition
import jp.mydns.kokoichi0206.data.repository.SakamichiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * UseCase of getMembers function in a repository.
 */
class GetPositionsUseCase @Inject constructor(
    private val repository: SakamichiRepository
) {
    operator fun invoke(title: String): Flow<Resource<List<jp.mydns.kokoichi0206.model.Position>>> = flow {
        try {
            emit(Resource.Loading<List<jp.mydns.kokoichi0206.model.Position>>())
            val positions = repository.getPositions(title).positions.map { it.toPosition() }
            emit(Resource.Success<List<jp.mydns.kokoichi0206.model.Position>>(positions))
        } catch (e: HttpException) {
            emit(Resource.Error<List<jp.mydns.kokoichi0206.model.Position>>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<jp.mydns.kokoichi0206.model.Position>>("Couldn't reach server. Check your network connection"))
        } catch (e: JsonDataException) {
            emit(Resource.Error("Something unexpected happened at server.\nPlease report to us."))
        }
    }
}