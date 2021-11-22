package io.kokoichi.sample.sakamichiapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.kokoichi.sample.sakamichiapp.domain.model.QuizRecord

@Database(
    entities = [QuizRecord::class],
    version = 1,
    exportSchema = false,
)

abstract class QuizRecordDatabase: RoomDatabase() {

    abstract val quizRecordDao: QuizRecordDao

    companion object {
        const val DATABASE_NAME = "quiz_record_db"
    }
}
