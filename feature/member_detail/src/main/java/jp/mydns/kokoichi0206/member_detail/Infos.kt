package jp.mydns.kokoichi0206.member_detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import jp.mydns.kokoichi0206.common.components.CustomDevider
import jp.mydns.kokoichi0206.feature.member_detail.R
import jp.mydns.kokoichi0206.member_detail.components.OneInfo
import jp.mydns.kokoichi0206.member_detail.components.RowTags

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

        Row(
            modifier = Modifier
                .padding(PADDING_HORIZONTAL, PADDING_VERTICAL)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(member.blogUrl))
                        context.startActivity(browserIntent)
                    },
                text = context.getString(R.string.detail_info_blog),
            )
        }
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