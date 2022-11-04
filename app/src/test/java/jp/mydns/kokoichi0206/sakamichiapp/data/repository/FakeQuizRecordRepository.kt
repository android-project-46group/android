package jp.mydns.kokoichi0206.sakamichiapp.data.repository

import jp.mydns.kokoichi0206.data.repository.QuizRecordRepository

class FakeQuizRecordRepository : QuizRecordRepository {

    private val recordItems = mutableListOf<jp.mydns.kokoichi0206.model.QuizRecord>()

    override suspend fun getRecordsByGroup(group: String): List<jp.mydns.kokoichi0206.model.QuizRecord> {
        return recordItems
            .filter { it.groupName == group }
    }

    override suspend fun getRecordByGroupAndType(group: String, type: String): jp.mydns.kokoichi0206.model.QuizRecord? {
        return recordItems
            .filter { it.groupName == group }
            .firstOrNull { it.type == type }
    }

    override suspend fun insertRecord(record: jp.mydns.kokoichi0206.model.QuizRecord) {
        recordItems.add(record)
    }
}
