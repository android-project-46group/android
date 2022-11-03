package jp.mydns.kokoichi0206.domain.usecase.quiz_record

import jp.mydns.kokoichi0206.data.repository.QuizRecordRepository

/**
 * UseCase of insert or update a quiz record.
 */
class InsertRecordUseCase(
    private val repository: QuizRecordRepository,
) {

    suspend operator fun invoke(record: jp.mydns.kokoichi0206.model.QuizRecord) {
        repository.insertRecord(record)
    }
}
