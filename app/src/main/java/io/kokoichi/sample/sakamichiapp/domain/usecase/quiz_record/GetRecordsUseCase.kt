package io.kokoichi.sample.sakamichiapp.domain.usecase.quiz_record

import io.kokoichi.sample.sakamichiapp.domain.model.QuizRecord
import io.kokoichi.sample.sakamichiapp.domain.repository.QuizRecordRepository

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
