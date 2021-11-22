package io.kokoichi.sample.sakamichiapp.domain.usecase.quiz_record

data class RecordUseCases(
    val getRecords: GetRecordsUseCase,
    val getRecord: GetRecordUseCase,
    val getAccuracy: GetAccuracyRateByGroupUseCase,
    val insertRecord: InsertRecordUseCase,
)
