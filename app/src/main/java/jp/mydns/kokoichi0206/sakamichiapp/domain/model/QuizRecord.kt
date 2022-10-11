package jp.mydns.kokoichi0206.sakamichiapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuizRecord(
    val groupName: String,
    val type: String,
    val correctNum: Int,
    val totalNum: Int,
    @PrimaryKey val id: Int? = null
)
