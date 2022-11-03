package jp.mydns.kokoichi0206.sakamichiapp.domain.usecase.quiz_record

import jp.mydns.kokoichi0206.sakamichiapp.domain.repository.QuizRecordRepository

/**
 * UseCase of get a quiz records for one group and one specific type.
 */
class GetRecordUseCase(
    private val repository: QuizRecordRepository
) {

    suspend operator fun invoke(
        group: String,
        type: String
    ): jp.mydns.kokoichi0206.model.QuizRecord {
        return repository.getRecordByGroupAndType(
            group = group, type = type
        ) ?: jp.mydns.kokoichi0206.model.QuizRecord(
            groupName = group,
            type = type,
            correctNum = 0,
            totalNum = 0,
        )
    }
}
