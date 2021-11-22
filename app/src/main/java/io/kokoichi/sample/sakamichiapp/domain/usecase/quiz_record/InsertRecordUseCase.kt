package io.kokoichi.sample.sakamichiapp.domain.usecase.quiz_record

import io.kokoichi.sample.sakamichiapp.domain.model.QuizRecord
import io.kokoichi.sample.sakamichiapp.domain.repository.QuizRecordRepository

/**
 * UseCase of insert or update a quiz record.
 */
class InsertRecordUseCase(
    private val repository: QuizRecordRepository
) {

    suspend operator fun invoke(record: QuizRecord) {
        repository.insertRecord(record)
    }
}
