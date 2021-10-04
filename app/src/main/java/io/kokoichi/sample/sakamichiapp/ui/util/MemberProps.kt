package io.kokoichi.sample.sakamichiapp.ui.util


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
 * 月日でソートするため、日付を大小に変換する関数
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
 * 各々の情報を表示する際のスタイル
 * sort タイプによって異なる
 *
 * DEFAULT: 何もなし
 * BIRTHDAY: 生年月日を表示
 * LINES: タイプによってラインを引く
 */
enum class ShowMemberStyle {
    DEFAULT, BIRTHDAY, LINES
}