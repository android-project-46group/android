package io.kokoichi.sample.sakamichiapp.ui.components

data class MemberProps(
    val name: String = "name",
    val name_ja: String? = "メンバー名",
    val birthday: String? = null,
    val group: String? = "nogizaka",
    val heigt: String? = "159cm",
    val bloodType: String = "不明",
    val generation: String = "不明"
)