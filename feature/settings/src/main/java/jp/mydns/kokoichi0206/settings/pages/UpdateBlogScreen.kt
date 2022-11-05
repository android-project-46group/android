package jp.mydns.kokoichi0206.settings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import jp.mydns.kokoichi0206.common.datamanager.DataStoreManager
import jp.mydns.kokoichi0206.feature.settings.R
import jp.mydns.kokoichi0206.settings.SettingsUiState
import jp.mydns.kokoichi0206.settings.TestTags
import kotlinx.coroutines.async

@Composable
fun UpdateBlogScreen(
    navController: NavHostController,
    uiState: SettingsUiState,
    onUpdateClicked: () -> Unit,
) {

    val context = LocalContext.current

    var openDialog by remember { mutableStateOf(false) }

    var isDeveloper by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        val storeVal = async {
            DataStoreManager.readBoolean(context, DataStoreManager.KEY_IS_DEVELOPER)
        }
        isDeveloper = storeVal.await()
    }
    // Outer Box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(uiState.themeType.subColor)
            .padding(10.dp)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TestTags.UPDATE_BLOG_TITLE),
                text = stringResource(R.string.update_blog_title),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
            )
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )
            Text(
                modifier = Modifier
                    .testTag(TestTags.UPDATE_BLOG_BODY),
                text = stringResource(R.string.update_blog_body),
                style = MaterialTheme.typography.h5,
            )
        }

        val buttonPaddingValue = 8.dp
        val buttonShape = RoundedCornerShape(5.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .clip(buttonShape)
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = uiState.themeType.baseColor,
                        shape = buttonShape
                    )
                    .testTag(TestTags.UPDATE_BLOG_OK_BUTTON),
                onClick = {
                    openDialog = true
                },
            ) {
                Text(
                    text = stringResource(R.string.update_blog_button_ok),
                    color = uiState.themeType.baseColor,
                )
            }

            Spacer(modifier = Modifier.width(buttonPaddingValue))

            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .clip(buttonShape)
                    .testTag(TestTags.UPDATE_BLOG_CANCEL_BUTTON)
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = uiState.themeType.baseColor,
                        shape = buttonShape
                    ),
                onClick = {
                    navController.popBackStack()
                },
            ) {
                Text(
                    text = stringResource(R.string.update_blog_button_cancel),
                    color = uiState.themeType.baseColor,
                )
            }
        }
    }
    if (openDialog) {
        if (isDeveloper) {
            // If you are the developer, you can update the blog.
            onUpdateClicked()
            // Show dialog
            CheckDialog(
                title = stringResource(R.string.update_blog_success_dialog_title),
                body = stringResource(R.string.update_blog_success_dialog_body),
                buttonColor = uiState.themeType.subColor,
                onButtonClicked = {
                    openDialog = !openDialog
                },
            )
        } else {
            // Show dialog
            CheckDialog(
                title = stringResource(R.string.update_blog_failure_dialog_title),
                body = stringResource(R.string.update_blog_failure_dialog_body),
                buttonColor = uiState.themeType.subColor,
                onButtonClicked = {
                    openDialog = !openDialog
                },
            )
        }
    }
}

@Composable
fun CheckDialog(
    title: String,
    body: String,
    buttonColor: Color,
    onButtonClicked: () -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    modifier = Modifier
                        .testTag(TestTags.UPDATE_BLOG_DIALOG_TITLE),
                    text = title,
                    color = Color.DarkGray,
                )
            },
            text = {
                Text(
                    modifier = Modifier
                        .testTag(TestTags.UPDATE_BLOG_DIALOG_BODY),
                    text = body,
                    color = Color.DarkGray,
                )
            },
            buttons = {
                val buttonPaddingValue = 12.dp
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = buttonPaddingValue),
                ) {
                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(5.dp))
                            .background(buttonColor)
                            .testTag(TestTags.UPDATE_BLOG_DIALOG_OK_BUTTON),
                        onClick = {
                            onButtonClicked()
                        }
                    ) {
                        Text(
                            modifier = Modifier,
                            text = stringResource(R.string.update_blog_dialog_button),
                            color = Color.White,
                        )
                    }
                }
            },
        )
    }
}

