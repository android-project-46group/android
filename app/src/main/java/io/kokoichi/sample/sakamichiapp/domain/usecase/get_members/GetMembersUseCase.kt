package io.kokoichi.sample.sakamichiapp.domain.usecase.get_members

import android.util.Log
import io.kokoichi.sample.sakamichiapp.common.Resource
import io.kokoichi.sample.sakamichiapp.data.remote.dto.toMember
import io.kokoichi.sample.sakamichiapp.domain.model.Member
import io.kokoichi.sample.sakamichiapp.domain.repository.SakamichiRepository
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
    operator fun invoke(groupName: String): Flow<Resource<List<Member>>> = flow {
        try {
            emit(Resource.Loading<List<Member>>())
            Log.d("hoge", repository.getMembers(groupName).members.toString())
            val members = repository.getMembers(groupName).members.map { it.toMember() }
            emit(Resource.Success<List<Member>>(members))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Member>>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error<List<Member>>("Couldn't reach server. Check your network connection"))
        }
    }
}