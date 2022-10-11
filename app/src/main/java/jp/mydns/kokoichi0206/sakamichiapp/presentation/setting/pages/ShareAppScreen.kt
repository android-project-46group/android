package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.pages

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceMedium
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.SettingsUiState
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.components.SettingTopBar
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Constants
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.TestTags

/**
 * Screen to share this application.
 */
@Composable
fun ShareAppScreen(
    navController: NavHostController,
    uiState: SettingsUiState,
) {
    // Top Bar
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SettingTopBar(
            themeType = uiState.themeType,
            text = stringResource(id = R.string.share_app_title),
            onArrowClick = {
                navController.popBackStack()
            }
        )
    }
    val activity = LocalContext.current as Activity
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.BottomBarPadding)
            .padding(SpaceSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        val chooserTitle = stringResource(id = R.string.share_app_intent_title)
        val chooserMessage =
            stringResource(
                id = R.string.share_app_intent_text,
                stringResource(id = R.string.app_name)
            ) + "\n" + stringResource(id = R.string.share_app_apk_link)
        // QR code
        Image(
            painter = painterResource(id = R.drawable.link_to_apk),
            contentDescription = stringResource(R.string.url_link),
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
                .testTag(TestTags.SHARE_APP_QR_CODE)
        )
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.share_app_message),
                style = MaterialTheme.typography.h5.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(SpaceSmall))
                    .background(color = Color.LightGray)
                    .clickable {
                        // Start a new intent
                        val intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, chooserMessage)
                            putExtra(Intent.EXTRA_TITLE, chooserTitle)
                            type = "text/plain"
                        }
                        activity.startActivity(Intent.createChooser(intent, null))
                    }
                    .padding(SpaceMedium)
                    .testTag(TestTags.SHARE_APP_MESSAGE)
            )
        }
    }
}
