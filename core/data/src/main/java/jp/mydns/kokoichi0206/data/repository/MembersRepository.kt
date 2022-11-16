package jp.mydns.kokoichi0206.data.repository

import jp.mydns.kokoichi0206.data.local.model.MemberEntity
import jp.mydns.kokoichi0206.model.Member

interface MembersRepository {

    suspend fun getMembersByGroup(group: String): List<MemberEntity>

    suspend fun insertMembers(members: List<Member>)
}
