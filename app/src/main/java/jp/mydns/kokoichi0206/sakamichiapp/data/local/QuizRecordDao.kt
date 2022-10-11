package jp.mydns.kokoichi0206.sakamichiapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.QuizRecord

@Dao
interface QuizRecordDao {

    @Query("SELECT * FROM QuizRecord WHERE groupName = :group")
    suspend fun getRecordsByGroup(group: String): List<QuizRecord>

    @Query("SELECT * FROM QuizRecord WHERE groupName = :group AND type = :type")
    suspend fun getRecordByGroupAndType(group: String, type: String): QuizRecord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizRecord(recordDao: QuizRecord)
}
