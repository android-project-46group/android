package jp.mydns.kokoichi0206.domain.usecase.get_members

import jp.mydns.kokoichi0206.common.Resource
import jp.mydns.kokoichi0206.data.remote.dto.toMember
import jp.mydns.kokoichi0206.data.repository.SakamichiRepository
import jp.mydns.kokoichi0206.model.Member
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * UseCase of getMembers function in a repository.
 */
class GetMembersUseCase @Inject constructor(
    private val repository: SakamichiRepository,
) {
    // ここで Members -> List<Member> に変えている
    operator fun invoke(groupName: String): Flow<Resource<List<Member>>> = flow {
        try {
            emit(Resource.Loading())
            val members = repository.getMembers(groupName).members.map { it.toMember() }
            emit(Resource.Success(members))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your network connection"))
        }
    }
}