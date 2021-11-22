package io.kokoichi.sample.sakamichiapp.data.repository

import io.kokoichi.sample.sakamichiapp.domain.model.QuizRecord
import io.kokoichi.sample.sakamichiapp.domain.repository.QuizRecordRepository

class FakeQuizRecordRepository : QuizRecordRepository {

    private val recordItems = mutableListOf<QuizRecord>()

    override suspend fun getRecordsByGroup(group: String): List<QuizRecord> {
        return recordItems
            .filter { it.groupName == group }
    }

    override suspend fun getRecordByGroupAndType(group: String, type: String): QuizRecord? {
        return recordItems
            .filter { it.groupName == group }
            .firstOrNull { it.type == type }
    }

    override suspend fun insertRecord(record: QuizRecord) {
        recordItems.add(record)
    }
}
