package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_detail

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_detail.components.OneInfo
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_detail.components.RowTags
import jp.mydns.kokoichi0206.common.components.CustomDevider

val PADDING_HORIZONTAL = 10.dp
val PADDING_VERTICAL = 4.dp

/**
 * Member Information
 */
@Composable
fun Infos(
    uiState: MemberDetailUiState
) {
    val member = uiState.member!!
    val context = LocalContext.current

    Column() {

        RowTags(uiState.tags)

        // Japanese Name
        Text(
            text = member.name,
            modifier = Modifier
                .padding(PADDING_HORIZONTAL * 3, 0.dp, PADDING_HORIZONTAL * 2, PADDING_VERTICAL),
            style = MaterialTheme.typography.h2,
        )

        // Double Divider
        CustomDevider(MaterialTheme.colors.secondary, 1.dp)
        Spacer(modifier = Modifier.height(3.dp))
        CustomDevider(MaterialTheme.colors.secondary, 1.dp)

        OneInfo(InfoKeys.HEIGHT.getStringResource(context), member.height)
        CustomDevider(MaterialTheme.colors.secondary, 1.dp)
        OneInfo(InfoKeys.BIRTHDAY.getStringResource(context), member.birthday)
        CustomDevider(MaterialTheme.colors.secondary, 1.dp)
        OneInfo(InfoKeys.BLOOD_TYPE.getStringResource(context), member.bloodType)
        CustomDevider(MaterialTheme.colors.secondary, 1.dp)

        // Experimental
        OneInfo(InfoKeys.GROUP_NAME.getStringResource(context), member.group!!)
        CustomDevider(MaterialTheme.colors.secondary, 1.dp)
    }
}

// Enum
// InfoKeys.BIRTHDAY.name = "BIRTHDAY"
// InfoKeys.BIRTHDAY.key = "生年月日："
enum class InfoKeys(val key: Int) {
    BIRTHDAY(R.string.detail_info_birthday),
    HEIGHT(R.string.detail_info_height),
    BLOOD_TYPE(R.string.detail_info_blood),
    GROUP_NAME(R.string.detail_info_group);

    fun getStringResource(context: Context): String {
        return context.getString(key)
    }
}