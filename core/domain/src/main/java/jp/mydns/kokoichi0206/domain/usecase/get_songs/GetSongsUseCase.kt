package jp.mydns.kokoichi0206.domain.usecase.get_songs

import jp.mydns.kokoichi0206.common.Resource
import jp.mydns.kokoichi0206.data.remote.dto.toSong
import jp.mydns.kokoichi0206.data.repository.SakamichiRepository
import jp.mydns.kokoichi0206.model.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * UseCase of getSongs function in a repository.
 */
class GetSongsUseCase @Inject constructor(
    private val repository: SakamichiRepository
) {
    operator fun invoke(groupName: String): Flow<Resource<List<Song>>> = flow {
        try {
            emit(Resource.Loading<List<jp.mydns.kokoichi0206.model.Song>>())
            val songs = repository.getSongs(groupName).songs.map { it.toSong() }
            emit(Resource.Success<List<jp.mydns.kokoichi0206.model.Song>>(songs))
        } catch (e: HttpException) {
            emit(Resource.Error<List<jp.mydns.kokoichi0206.model.Song>>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<jp.mydns.kokoichi0206.model.Song>>("Couldn't reach server. Check your network connection"))
        }
    }
}