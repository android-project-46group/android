package jp.mydns.kokoichi0206.sakamichiapp.domain.repository

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.QuizRecord

interface QuizRecordRepository {

    suspend fun getRecordsByGroup(group: String): List<QuizRecord>

    suspend fun getRecordByGroupAndType(group: String, type: String): QuizRecord?

    suspend fun insertRecord(record: QuizRecord)
}
