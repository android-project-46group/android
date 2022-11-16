package jp.mydns.kokoichi0206.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.mydns.kokoichi0206.data.local.model.MemberEntity

@Database(
    entities = [MemberEntity::class],
    version = 1,
    exportSchema = false,
)

abstract class MembersDatabase: RoomDatabase() {

    abstract val membersDao: MembersDao

    companion object {
        const val DATABASE_NAME = "members_db"
    }
}
