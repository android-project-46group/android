package jp.mydns.kokoichi0206.data.repository

import jp.mydns.kokoichi0206.data.local.QuizRecordDao
import jp.mydns.kokoichi0206.model.QuizRecord

/**
 * Implementation of repository interface using actual Database query.
 */
class QuizRecordRepositoryImpl(
    private val recordDao: QuizRecordDao
): QuizRecordRepository {

    override suspend fun getRecordsByGroup(group: String): List<QuizRecord> {
        return recordDao.getRecordsByGroup(group)
    }

    override suspend fun getRecordByGroupAndType(group: String, type: String): QuizRecord? {
        return recordDao.getRecordByGroupAndType(group, type)
    }

    override suspend fun insertRecord(record: QuizRecord) {
        return recordDao.insertQuizRecord(record)
    }
}
