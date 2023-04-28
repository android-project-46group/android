package jp.mydns.kokoichi0206.settings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jp.mydns.kokoichi0206.common.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.feature.settings.R
import jp.mydns.kokoichi0206.model.Member
import jp.mydns.kokoichi0206.settings.SettingsUiState

@Composable
fun FaveSettingConfirmScreen(
    uiState: SettingsUiState,
    selected: Member,
    onConfirmClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(96.dp)
                .border(
                    width = 3.dp,
                    color = Color.Gray.copy(alpha = 0.3f),
                ),
            model = selected.imgUrl,
            contentDescription = "picture of ${selected.name}",
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text =  stringResource(R.string.my_fave_confirm_name, selected.name),
            fontSize = 14.sp,
            color = Color.Gray.copy(alpha = 0.9f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.my_fave_confirm_confirm),
            color = uiState.themeType.fontColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable {
                    onConfirmClicked()
                }
                .background(uiState.themeType.subColor)
                .padding(horizontal = 64.dp, vertical = 12.dp)
                .width(96.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.my_fave_confirm_cancel),
            color = Color.Gray.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable {
                    onCancelClicked()
                }
                .border(
                    width = 1.dp,
                    color = Color.Gray.copy(alpha = 0.6f)
                )
                .padding(horizontal = 64.dp, vertical = 12.dp)
                .width(96.dp)
        )
    }
}

@Preview
@Composable
fun FaveSettingConfirmScreenPreview() {
    val member = Member(
        name = "坂道 太郎",
        imgUrl = "https://avatars.githubusercontent.com/u/52474650?v=4",
    )

    CustomSakaTheme {
        FaveSettingConfirmScreen(
            uiState = SettingsUiState(),
            member,
        )
    }
}
