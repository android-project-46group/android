package jp.mydns.kokoichi0206.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuizRecordDao {

    @Query("SELECT * FROM QuizRecord WHERE groupName = :group")
    suspend fun getRecordsByGroup(group: String): List<jp.mydns.kokoichi0206.model.QuizRecord>

    @Query("SELECT * FROM QuizRecord WHERE groupName = :group AND type = :type")
    suspend fun getRecordByGroupAndType(group: String, type: String): jp.mydns.kokoichi0206.model.QuizRecord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizRecord(recordDao: jp.mydns.kokoichi0206.model.QuizRecord)
}
