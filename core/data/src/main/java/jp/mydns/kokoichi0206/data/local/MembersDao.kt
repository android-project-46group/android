package jp.mydns.kokoichi0206.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.mydns.kokoichi0206.data.local.model.MemberEntity

@Dao
interface MembersDao {

    @Query("SELECT * FROM MemberEntity WHERE groupName = :groupName")
    suspend fun getMembers(groupName: String): List<MemberEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMembers(membersDao: List<MemberEntity>)
}
