package jp.mydns.kokoichi0206.domain.usecase.quiz_record

import jp.mydns.kokoichi0206.data.repository.QuizRecordRepository

/**
 * UseCase of get quiz records for one group.
 */
class GetRecordsUseCase(
    private val repository: QuizRecordRepository,
) {

    suspend operator fun invoke(
        group: String
    ): List<jp.mydns.kokoichi0206.model.QuizRecord> {
        val records = repository.getRecordsByGroup(group)
        return if(records.isEmpty()) {
            emptyList()
        } else {
            records
        }
    }
}
