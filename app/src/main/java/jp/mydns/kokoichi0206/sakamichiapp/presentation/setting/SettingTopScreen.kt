package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.components.VersionInfo
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.IconSizeMedium
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceTiny
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.Typography
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Constants.BottomPadding
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.TestTags

@Composable
fun SettingTopScreen(
    navController: NavController,
    navigationList: List<SettingNavigation>,
    viewModel: SettingsViewModel,
    uiState: SettingsUiState,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar()

        Spacer(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .background(uiState.themeType.subColor)
        )
        LazyColumn {
            item {
                Divider(
                    color = uiState.themeType.subColor,
                    thickness = 1.dp
                )
            }

            items(navigationList) { item ->
                SettingRow(
                    text = context.getString(item.name),
                    onclick = {
                        if (navController.currentDestination?.route !in navigationList.map { it.route }) {
                            navController.navigate(item.route)
                        }
                    }
                )
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(uiState.themeType.subColor)
        )

        VersionInfo(
            version = uiState.version,
            borderColor = uiState.themeType.subColor,
        ) {
            viewModel.writeIsDevTrue(context)
        }
    }
}

@Composable
fun TopBar() {
    Text(
        modifier = Modifier
            .padding(top = SpaceSmall, bottom = SpaceTiny)
            .testTag(TestTags.SETTING_TITLE),
        text = stringResource(R.string.setting_screen_title),
        style = Typography.h5,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun SettingRow(
    text: String,
    onclick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onclick()
            }
            .padding(SpaceSmall)
            .testTag(text),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceSmall),
            text = text,
            style = Typography.body2,
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = stringResource(R.string.right_arrow),
            tint = Color.LightGray,
            modifier = Modifier
                .size(IconSizeMedium)
        )
    }
}
