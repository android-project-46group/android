package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import jp.mydns.kokoichi0206.sakamichiapp.BuildConfig
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.SettingsUiState
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.components.SettingTopBar
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceLarge
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceMedium
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.TextGray
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Constants
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.TestTags

/**
 * Screen to share this application.
 */
@Composable
fun AboutAppScreen(
    navController: NavHostController,
    uiState: SettingsUiState,
) {
    val context = LocalContext.current

    // Top Bar
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SettingTopBar(
            themeType = uiState.themeType,
            text = stringResource(id = R.string.setting_about_app),
            onArrowClick = {
                navController.popBackStack()
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.BottomBarPadding)
            .padding(SpaceSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        // App Icon
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = stringResource(R.string.setting_about_app),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .testTag(TestTags.ABOUT_APP_APP_ICON)
        )

        Spacer(modifier = Modifier.height(SpaceMedium))

        // Version
        Text(
            text = context.getString(R.string.about_app_version),
            color = TextGray,
        )
        Text(
            text = uiState.version,
            color = TextGray,
        )

        Spacer(modifier = Modifier.height(SpaceLarge))

        // User ID
        Text(
            text = context.getString(R.string.about_app_user_id),
            color = TextGray,
        )
        Text(
            text = uiState.userId,
            color = TextGray,
        )
    }
}
