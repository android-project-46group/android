package jp.mydns.kokoichi0206.data.repository

import jp.mydns.kokoichi0206.data.local.MembersDao
import jp.mydns.kokoichi0206.data.local.model.MemberEntity
import jp.mydns.kokoichi0206.model.Member

/**
 * Implementation of repository interface using actual Database query.
 */
class MembersRepositoryImpl(
    private val membersDao: MembersDao
) : MembersRepository {

    override suspend fun getMembersByGroup(group: String): List<MemberEntity> {
        return membersDao.getMembers(group)
    }

    override suspend fun insertMembers(members: List<Member>) {
        return membersDao.insertMembers(members.map {
            MemberEntity(
                blogUrl = it.blogUrl,
                bloodType = it.bloodType,
                generation = it.generation,
                height = it.height,
                imgUrl = it.imgUrl,
                name = it.name,
                birthday = it.birthday,
                groupName = it.group ?: "",
            )
        })
    }

    override suspend fun deleteMembers(group: String) {
        return membersDao.deleteMembers(group)
    }
}
