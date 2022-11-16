package jp.mydns.kokoichi0206.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.mydns.kokoichi0206.model.Member

@Entity
data class MemberEntity(
    val blogUrl: String,
    val bloodType: String,
    val generation: String,
    val height: String,
    val imgUrl: String,
    val name: String,
    val birthday: String,
    val groupName: String,
    @PrimaryKey val id: Int? = null
)

fun MemberEntity.asExternalModel() = Member(
    blogUrl = blogUrl,
    bloodType = bloodType,
    generation = generation,
    height = height,
    imgUrl = imgUrl,
    name = name,
    birthday = birthday,
    group = groupName,
)
