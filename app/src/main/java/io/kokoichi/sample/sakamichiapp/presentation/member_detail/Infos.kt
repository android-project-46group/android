package io.kokoichi.sample.sakamichiapp.presentation.member_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kokoichi.sample.sakamichiapp.presentation.member_detail.components.OneInfo
import io.kokoichi.sample.sakamichiapp.presentation.member_detail.components.RowTags
import io.kokoichi.sample.sakamichiapp.presentation.util.components.CustomDevider

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

        OneInfo(InfoKeys.HEIGHT.key, member.height)
        CustomDevider(MaterialTheme.colors.secondary, 1.dp)
        OneInfo(InfoKeys.BIRTHDAY.key, member.birthday)
        CustomDevider(MaterialTheme.colors.secondary, 1.dp)
        OneInfo(InfoKeys.BLOOD_TYPE.key, member.bloodType)
        CustomDevider(MaterialTheme.colors.secondary, 1.dp)

        // Experimental
        OneInfo(InfoKeys.GROUP_NAME.key, member.group!!)
        CustomDevider(MaterialTheme.colors.secondary, 1.dp)
    }
}

// Enum
// InfoKeys.BIRTHDAY.name = "BIRTHDAY"
// InfoKeys.BIRTHDAY.key = "生年月日："
enum class InfoKeys(val key: String) {
    BIRTHDAY("生年月日"),
    HEIGHT("身長"),
    BLOOD_TYPE("血液型"),
    GROUP_NAME("グループ名"),
}