package jp.mydns.kokoichi0206.domain.usecase.get_members

import android.util.Log
import com.squareup.moshi.JsonDataException
import jp.mydns.kokoichi0206.common.Resource
import jp.mydns.kokoichi0206.data.local.model.asExternalModel
import jp.mydns.kokoichi0206.data.remote.dto.toMember
import jp.mydns.kokoichi0206.data.repository.MembersRepository
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
    private val dbRepository: MembersRepository,
) {
    companion object {
        private val TAG = GetMembersUseCase::class.java.simpleName
    }

    // ここで Members -> List<Member> に変えている
    operator fun invoke(groupName: String, force: Boolean = false): Flow<Resource<List<Member>>> = flow {
        try {
            if (!force) {
                dbRepository.getMembersByGroup(groupName).map {
                    it.asExternalModel()
                }.let { members ->
                    if (members.isNotEmpty()) {
                        Log.d(TAG, "Members of $groupName already exist.")
                        emit(Resource.Success(members))
                        return@flow
                    }
                }
            }
            emit(Resource.Loading())
            // Call API
            val members = repository.getMembers(groupName).members.map { it.toMember() }
            Log.d(TAG, "API repository.getMembers($groupName) called")
            if (members.isEmpty()) {
                emit(Resource.Error("members from api is empty."))
                return@flow
            }
            // Delete all data from local db
            dbRepository.deleteMembers(groupName)
            emit(Resource.Success(members))
            // Insert data to local db
            dbRepository.insertMembers(
                members.map {
                    it.copy(group = groupName)
                }
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your network connection."))
        } catch (e: JsonDataException) {
            emit(Resource.Error("Something unexpected happened at server.\nPlease report to us."))
        }
    }
}