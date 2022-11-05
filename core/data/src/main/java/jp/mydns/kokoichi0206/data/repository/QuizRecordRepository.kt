package jp.mydns.kokoichi0206.data.repository

interface QuizRecordRepository {

    suspend fun getRecordsByGroup(group: String): List<jp.mydns.kokoichi0206.model.QuizRecord>

    suspend fun getRecordByGroupAndType(group: String, type: String): jp.mydns.kokoichi0206.model.QuizRecord?

    suspend fun insertRecord(record: jp.mydns.kokoichi0206.model.QuizRecord)
}
