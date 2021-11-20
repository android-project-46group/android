package io.kokoichi.sample.sakamichiapp.domain.usecase.quiz_record

import io.kokoichi.sample.sakamichiapp.domain.repository.QuizRecordRepository

/**
 * UseCase of get accuracy of quizzes for one group.
 */
class GetAccuracyRateByGroupUseCase(
    private val repository: QuizRecordRepository
) {

    suspend operator fun invoke(
        group: String
    ): List<Int> {
        val records = repository.getRecordsByGroup(group)
        var correct = 0
        var total = 0
        records.forEach { record ->
            correct += record.correctNum
            total += record.totalNum
        }
        return listOf(correct, total)
    }
}
