package io.kokoichi.sample.sakamichiapp.presentation.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.kokoichi.sample.sakamichiapp.BuildConfig
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.Typography
import io.kokoichi.sample.sakamichiapp.presentation.util.Constants
import io.kokoichi.sample.sakamichiapp.presentation.util.DataStoreManager
import io.kokoichi.sample.sakamichiapp.presentation.util.TestTags
import io.kokoichi.sample.sakamichiapp.presentation.util.getMilliSecFromLocalTime
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalTime

@Composable
fun SettingTopScreen(
    navController: NavController,
    navigationList: List<SettingNavigation>,
    viewModel: SettingsViewModel,
    uiState: SettingsUiState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
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
                    text = item.name,
                    onclick = {
                        navController.navigate(item.route)
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

        val context = LocalContext.current
        VersionInfo {
            viewModel.writeIsDevTrue(context)
        }
        Spacer(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .background(uiState.themeType.subColor)
        )
    }
}

@Composable
fun TopBar() {
    Text(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 5.dp)
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
            .padding(10.dp)
            .testTag(text),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = text,
            style = Typography.body2,
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "right arrow",
            tint = Color.LightGray,
            modifier = Modifier
                .size(30.dp)
        )
    }
}

@Composable
fun VersionInfo(onIsDevChanged: () -> Unit) {

    val context = LocalContext.current
    // For click timing
    var touchNum by remember { mutableStateOf(0) }
    var lastTouchedTime by remember { mutableStateOf(0) }

    // For snackBar
    val snackbarCoroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var snackBarJob: Job? = null
    val thresholdMillSec = 500
    val needTap = 7

    var isDeveloper by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        val storeVal = async {
            DataStoreManager.readBoolean(context, DataStoreManager.KEY_IS_DEVELOPER)
        }
        isDeveloper = storeVal.await()
    }

    val successMessage = stringResource(R.string.developer_success_snack_bar_text)
    val onClick = when (isDeveloper) {
        false -> {
            {
                val current = getMilliSecFromLocalTime(LocalTime.now())
                if (current - lastTouchedTime < thresholdMillSec) {
                    touchNum += 1
                } else {
                    touchNum = 1
                }
                lastTouchedTime = current
                // If the count is near the needTapNum, show snack bar.
                if (touchNum > 3) {
                    snackBarJob?.cancel()
                    snackBarJob = snackbarCoroutineScope.launch {
                        snackbarHostState.showSnackbar("You are now ${needTap - touchNum} steps away from being a developer.")
                    }
                }
                // If the count is over the needTapNum, show snack bar with special message.
                if (touchNum >= needTap) {
                    snackBarJob?.cancel()
                    snackBarJob = snackbarCoroutineScope.launch {
                        onIsDevChanged()
                        snackbarHostState.showSnackbar(successMessage)
                    }
                }
            }
        }
        true -> {
            {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(15.dp)
            .testTag(TestTags.SETTING_VERSION),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "バージョン情報 ${BuildConfig.VERSION_NAME}",
            style = Typography.body2,
            textAlign = TextAlign.Center,
        )
    }

    SnackbarSetting(snackbarHostState)
}

@Composable
fun SnackbarSetting(
    snackbarHostState: SnackbarHostState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.BottomBarPadding)
    ) {
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState,
            snackbar = { snackbarData: SnackbarData ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Color.White),
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentSize()
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(5.dp)
                                .testTag(TestTags.SNACK_BAR_TEXT),
                            text = snackbarData.message,
                            style = MaterialTheme.typography.body2.copy(
                                color = Color.DarkGray,
                            ),
                        )
                    }
                }
            }
        )
    }
}
