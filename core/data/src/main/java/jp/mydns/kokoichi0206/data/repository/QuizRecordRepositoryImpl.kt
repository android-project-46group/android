package jp.mydns.kokoichi0206.data.repository

import jp.mydns.kokoichi0206.data.local.QuizRecordDao
import repository.QuizRecordRepository

/**
 * Implementation of repository interface using actual Database query.
 */
class QuizRecordRepositoryImpl(
    private val recordDao: QuizRecordDao
): repository.QuizRecordRepository {

    override suspend fun getRecordsByGroup(group: String): List<jp.mydns.kokoichi0206.model.QuizRecord> {
        return recordDao.getRecordsByGroup(group)
    }

    override suspend fun getRecordByGroupAndType(group: String, type: String): jp.mydns.kokoichi0206.model.QuizRecord? {
        return recordDao.getRecordByGroupAndType(group, type)
    }

    override suspend fun insertRecord(record: jp.mydns.kokoichi0206.model.QuizRecord) {
        return recordDao.insertQuizRecord(record)
    }
}
