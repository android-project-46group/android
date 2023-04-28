package jp.mydns.kokoichi0206.settings.pages

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import jp.mydns.kokoichi0206.common.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.feature.settings.R
import jp.mydns.kokoichi0206.model.Member
import jp.mydns.kokoichi0206.settings.SettingScreen
import jp.mydns.kokoichi0206.settings.SettingsUiState
import jp.mydns.kokoichi0206.settings.components.SettingTopBar

@Composable
fun FaveScreen(
    navController: NavHostController,
    onFaveNavigated: () -> Unit = {},
    onImageSelected: (Uri) -> Unit = {},
    uiState: SettingsUiState,
) {
    LaunchedEffect(true) {
        onFaveNavigated()
    }

    // Top Bar
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SettingTopBar(
            themeType = uiState.themeType,
            text = stringResource(id = R.string.my_fave),
            onArrowClick = {
                navController.popBackStack()
            }
        )
    }

    var imgUri by remember {
        mutableStateOf<Uri?>(
            uiState.faveURI ?: if (uiState.fave == null) null else Uri.parse(uiState.fave.imgUrl)
        )
    }

    val context = LocalContext.current

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                imgUri = it

                onImageSelected(uri)

                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION,
                )
            }
        },
    )


    val size = 240.dp

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(size)
                .border(
                    width = 3.dp,
                    color = Color.Gray.copy(alpha = 0.3f),
                )
                .clickable {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
            model = imgUri,
            contentDescription = "my fave's picture",
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = uiState.fave?.name ?: "46",
            fontSize = 36.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .background(uiState.themeType.subColor)
                .clickable {
                    navController.navigate(SettingScreen.MyFaveSettingScreen.route)
                }
                .padding(16.dp),
            text = context.resources.getString(R.string.my_fave_set_member),
            fontSize = 24.sp,
            color = uiState.themeType.fontColor,
        )
    }
}

@Preview
@Composable
fun FaveScreenPreview() {
    val navController = NavHostController(LocalContext.current)

    val member = Member(
        name = "坂道 太郎",
        imgUrl = "https://avatars.githubusercontent.com/u/52474650?v=4",
    )
    val uiState = SettingsUiState(
        fave = member,
    )

    CustomSakaTheme {
        FaveScreen(
            navController = navController,
            uiState = uiState,
        )
    }
}
