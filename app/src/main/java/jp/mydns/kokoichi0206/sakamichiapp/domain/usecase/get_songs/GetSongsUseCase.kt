package jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.get_songs

import jp.mydns.kokoichi0206.sakamichiapp.common.Resource
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.dto.toSong
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Song
import jp.mydns.kokoichi0206.sakamichiapp.domain.repository.SakamichiRepository
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
            emit(Resource.Loading<List<Song>>())
            val songs = repository.getSongs(groupName).songs.map { it.toSong() }
            emit(Resource.Success<List<Song>>(songs))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Song>>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<Song>>("Couldn't reach server. Check your network connection"))
        }
    }
}