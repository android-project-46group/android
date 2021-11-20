package io.kokoichi.sample.sakamichiapp.domain.usecase.quiz_record

import io.kokoichi.sample.sakamichiapp.domain.model.QuizRecord
import io.kokoichi.sample.sakamichiapp.domain.repository.QuizRecordRepository

/**
 * UseCase of get a quiz records for one group and one specific type.
 */
class GetRecordUseCase(
    private val repository: QuizRecordRepository
) {

    suspend operator fun invoke(
        group: String,
        type: String
    ): QuizRecord {
        return repository.getRecordByGroupAndType(
            group = group, type = type
        ) ?: QuizRecord(
            groupName = group,
            type = type,
            correctNum = 0,
            totalNum = 0,
        )
    }
}
