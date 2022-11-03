package jp.mydns.kokoichi0206.sakamichiapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [jp.mydns.kokoichi0206.model.QuizRecord::class],
    version = 1,
    exportSchema = false,
)

abstract class QuizRecordDatabase: RoomDatabase() {

    abstract val quizRecordDao: QuizRecordDao

    companion object {
        const val DATABASE_NAME = "quiz_record_db"
    }
}
