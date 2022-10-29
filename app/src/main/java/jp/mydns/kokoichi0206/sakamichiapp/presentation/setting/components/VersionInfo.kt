package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceMedium
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.Typography
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Constants
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.DataStoreManager
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.TestTags
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.getMilliSecFromLocalTime
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalTime

@Composable
fun VersionInfo(
    version: String = "1.0.0",
    borderColor: Color = Color.DarkGray,
    onIsDevChanged: () -> Unit,
) {

    val context = LocalContext.current
    // For click timing
    var touchNum by remember { mutableStateOf(0) }
    var lastTouchedTime by remember { mutableStateOf(0) }

    // For snackBar
    val snackbarCoroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var snackBarJob: Job? by remember { mutableStateOf(null) }

    var isDeveloper by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        val storeVal = async {
            DataStoreManager.readBoolean(context, DataStoreManager.KEY_IS_DEVELOPER)
        }
        isDeveloper = storeVal.await()
    }

    val successMessage = stringResource(R.string.developer_success_snack_bar_text)
    val onTheWayMessage = stringResource(
        id = R.string.developer_on_the_way_snack_bar_text,
        Constants.NEED_TAP_NUM_TO_BE_DEVELOPER - touchNum
    )
    val onClick = when (isDeveloper) {
        false -> {
            {
                val current = getMilliSecFromLocalTime(LocalTime.now())
                if (current - lastTouchedTime < Constants.CANCELLATION_THRESHOLD_MILLI_TIMES_OF_DEVELOPER) {
                    touchNum += 1
                } else {
                    touchNum = 1
                }
                lastTouchedTime = current
                // If the count is over the needed num, show snack bar with special message.
                if (touchNum >= Constants.NEED_TAP_NUM_TO_BE_DEVELOPER) {
                    snackBarJob?.cancel()
                    snackBarJob = snackbarCoroutineScope.launch {
                        onIsDevChanged()
                        snackbarHostState.showSnackbar(successMessage)
                        isDeveloper = true
                    }
                } else if (touchNum > Constants.NEED_TAP_NUM_TO_SHOW_SNACK_BAR) {
                    // If the count is near the needed num, show snack bar.
                    snackBarJob?.cancel()
                    snackBarJob = snackbarCoroutineScope.launch {
                        snackbarHostState.showSnackbar(onTheWayMessage)
                    }
                }

            }
        }
        true -> {
            {}
        }
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(SpaceMedium)
            .testTag(TestTags.SETTING_VERSION),
        text = stringResource(R.string.version_info_in_settings, version),
        style = Typography.body2,
        textAlign = TextAlign.Center,
    )

    SnackbarSetting(
        snackbarHostState = snackbarHostState,
        borderColor = borderColor,
    )
}
