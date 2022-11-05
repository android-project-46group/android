package jp.mydns.kokoichi0206.settings.pages

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
import jp.mydns.kokoichi0206.feature.settings.R
import jp.mydns.kokoichi0206.common.ui.theme.SpaceMedium
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import jp.mydns.kokoichi0206.settings.SettingsUiState
import jp.mydns.kokoichi0206.settings.components.SettingTopBar
import jp.mydns.kokoichi0206.common.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.settings.TestTags

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
            .padding(SpaceSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        val chooserTitle = stringResource(id = R.string.share_app_intent_title)
        val chooserMessage =
            stringResource(
                id = R.string.share_app_intent_text,
                uiState.appName,
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
