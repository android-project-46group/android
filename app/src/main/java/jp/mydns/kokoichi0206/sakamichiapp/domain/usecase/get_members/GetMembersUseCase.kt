package jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.get_members

import jp.mydns.kokoichi0206.sakamichiapp.common.Resource
import jp.mydns.kokoichi0206.sakamichiapp.data.remote.dto.toMember
import jp.mydns.kokoichi0206.sakamichiapp.domain.repository.SakamichiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * UseCase of getMembers function in a repository.
 */
class GetMembersUseCase @Inject constructor(
    private val repository: SakamichiRepository
) {
    // ここで Members -> List<Member> に変えている
    operator fun invoke(groupName: String): Flow<Resource<List<jp.mydns.kokoichi0206.model.Member>>> = flow {
        try {
            emit(Resource.Loading<List<jp.mydns.kokoichi0206.model.Member>>())
            val members = repository.getMembers(groupName).members.map { it.toMember() }
            emit(Resource.Success<List<jp.mydns.kokoichi0206.model.Member>>(members))
        } catch (e: HttpException) {
            emit(Resource.Error<List<jp.mydns.kokoichi0206.model.Member>>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<jp.mydns.kokoichi0206.model.Member>>("Couldn't reach server. Check your network connection"))
        }
    }
}