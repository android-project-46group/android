package io.kokoichi.sample.sakamichiapp.presentation.setting

data class SettingsUiState(
    val records: MutableList<Record> = mutableListOf()
)

data class Record(
    val group: String,
    val correct: Int,
    val total: Int,
)
