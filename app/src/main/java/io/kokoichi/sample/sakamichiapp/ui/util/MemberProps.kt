package io.kokoichi.sample.sakamichiapp.ui.util

import io.kokoichi.sample.sakamichiapp.gSelectedGeneration
import io.kokoichi.sample.sakamichiapp.members
import io.kokoichi.sample.sakamichiapp.showingMembers

data class Member(
    val name: String = "name",
    val name_ja: String? = "メンバー名",
    val birthday: String? = null,
    val b_strength: Int? = null,
    val imgUrl: String? = null,
    val height: String? = "159cm",
    val bloodType: String = "不明",
    val generation: String,
)

/**
 * 絞り込みを行う
 * グローバルに関する変数に関しての変更
 */
fun sortShowingMembers(narrowValGen: String) {
    gSelectedGeneration = narrowValGen

    showingMembers = mutableListOf()
    for (member in members) {
        if (member.generation == narrowValGen) {
            showingMembers.add(member)
        }
    }
}

/**
 * 月日でソートするための関数
 * 2000年4月18日 → 4 * 100 + 18 = 418
 */
fun birthdayStrength(birthday: String): Int {
    var s = birthday.split("年")
    val year = s[0]
    var d = s[1].split("月")
    val month = d[0].toInt()
    val day = d[1].split("日")[0].toInt()
    return 100 * month + day
}

/**
 * DEFAULT: 何もなし
 * BIRTHDAY: 生年月日を表示
 * LINES: タイプによってラインを引く
 */
enum class ShowMemberStyle {
    DEFAULT, BIRTHDAY, LINES
}