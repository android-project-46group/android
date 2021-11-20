package io.kokoichi.sample.sakamichiapp.domain.repository

import io.kokoichi.sample.sakamichiapp.domain.model.QuizRecord

interface QuizRecordRepository {

    suspend fun getRecordsByGroup(group: String): List<QuizRecord>

    suspend fun getRecordByGroupAndType(group: String, type: String): QuizRecord?

    suspend fun insertRecord(record: QuizRecord)
}
