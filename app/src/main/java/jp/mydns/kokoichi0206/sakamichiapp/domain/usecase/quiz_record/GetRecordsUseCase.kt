package jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.quiz_record

import jp.mydns.kokoichi0206.sakamichiapp.domain.model.QuizRecord
import jp.mydns.kokoichi0206.sakamichiapp.domain.repository.QuizRecordRepository

/**
 * UseCase of get quiz records for one group.
 */
class GetRecordsUseCase(
    private val repository: QuizRecordRepository
) {

    suspend operator fun invoke(
        group: String
    ): List<QuizRecord> {
        val records = repository.getRecordsByGroup(group)
        return if(records.isEmpty()) {
            emptyList()
        } else {
            records
        }
    }
}
