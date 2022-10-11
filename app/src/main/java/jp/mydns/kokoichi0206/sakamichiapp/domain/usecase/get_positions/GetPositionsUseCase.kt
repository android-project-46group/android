package jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.get_positions

import jp.mydns.kokoichi0206.sakamichiapp.common.Resource
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.dto.toPosition
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Position
import jp.mydns.kokoichi0206.sakamichiapp.domain.repository.SakamichiRepository
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
    operator fun invoke(title: String): Flow<Resource<List<Position>>> = flow {
        try {
            emit(Resource.Loading<List<Position>>())
            val positions = repository.getPositions(title).positions.map { it.toPosition() }
            emit(Resource.Success<List<Position>>(positions))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Position>>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<Position>>("Couldn't reach server. Check your network connection"))
        }
    }
}