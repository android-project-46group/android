package io.kokoichi.sample.sakamichiapp.domain.model

/**
 * Data class for one member in business logic.
 */
data class Member(
    val blogUrl: String,
    val bloodType: String,
    val generation: String,
    val height: String,
    val imgUrl: String,
    val name: String,
    val birthday: String,
    var group: String? = null,
)
