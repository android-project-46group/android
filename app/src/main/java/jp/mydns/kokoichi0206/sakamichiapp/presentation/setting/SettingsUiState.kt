package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting

import android.content.Context
import androidx.compose.ui.graphics.Color
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.*
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.DataStoreManager

data class SettingsUiState(
    val records: MutableList<Record> = mutableListOf(),
    val themeType: ThemeType = ThemeType.BasicNight,
    val version: String = "1.0.0",
    val userId: String = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
)

data class Record(
    val group: String,
    val correct: Int,
    val total: Int,
)

sealed class ThemeType(
    val baseColor: Color,
    val subColor: Color,
    val fontColor: Color = Color.White,
    val name: String,
) {
    object BasicNight : ThemeType(
        baseColor = Color.DarkGray,
        subColor = Color.LightGray.copy(alpha = 0.4f),
        fontColor = Color.Black,
        name = ThemeNames.DEFAULT_NIGHT,
    )

    object Sakurazaka : ThemeType(
        baseColor = BaseColorS,
        subColor = SubColorS,
        name = ThemeNames.SAKURAZAKA,
    )

    object Nogizaka : ThemeType(
        baseColor = BaseColorN,
        subColor = SubColorN,
        name = ThemeNames.NOGIZAKA,
    )

    object Hinata : ThemeType(
        baseColor = BaseColorH,
        subColor = SubColorH,
        name = ThemeNames.HINATAZAKA,
    )

    object Keyaki : ThemeType(
        baseColor = BaseColorK,
        subColor = SubColorK,
        name = ThemeNames.KEYAKIZAKA,
    )
}

object ThemeNames {
    const val DEFAULT_NIGHT = "夜"
    const val SAKURAZAKA = "さくら"
    const val NOGIZAKA = "むらさき"
    const val HINATAZAKA = "そら"
    const val KEYAKIZAKA = "みどり"
}

val themeTypes = listOf(
    ThemeType.BasicNight,
    ThemeType.Sakurazaka,
    ThemeType.Nogizaka,
    ThemeType.Hinata,
    ThemeType.Keyaki,
)

/**
 * Get BaseColor of ThemeType from DataStore.
 *
 * @param context Context
 * @return BaseColor of the saved themeType
 */
suspend fun getBaseColorFromDS(context: Context): Color {
    val type = DataStoreManager.readString(
        context = context,
        key = DataStoreManager.KEY_THEME_GROUP,
    )
    return getBaseColorInThemeTypesFromString(type)
}

/**
 * Get SubColor of ThemeType from DataStore.
 *
 * @param context Context
 * @return SubColor of the saved themeType
 */
suspend fun getSubColorFromDS(context: Context): Color {
    val type = DataStoreManager.readString(
        context = context,
        key = DataStoreManager.KEY_THEME_GROUP,
    )
    return getSubColorInThemeTypesFromString(type)
}

/**
 * Get BaseColor from String of ThemeType.name.
 *
 * @param type String of ThemeType.name
 * @return BaseColor (if the name exists), BaseColor of BasicNight (else)
 */
fun getBaseColorInThemeTypesFromString(type: String): Color {
    val theme = themeTypes.firstOrNull { it.name == type }
    return theme?.baseColor ?: ThemeType.BasicNight.baseColor
}

/**
 * Get SubColor from String of ThemeType.name.
 *
 * @param type String of ThemeType.name
 * @return SubColor (if the name exists), BaseColor of BasicNight (else)
 */
fun getSubColorInThemeTypesFromString(type: String): Color {
    val theme = themeTypes.firstOrNull { it.name == type }
    return theme?.subColor ?: ThemeType.BasicNight.subColor
}
