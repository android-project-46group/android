package jp.mydns.kokoichi0206.settings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import jp.mydns.kokoichi0206.common.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.feature.settings.R
import jp.mydns.kokoichi0206.model.Member
import jp.mydns.kokoichi0206.settings.SettingsUiState
import jp.mydns.kokoichi0206.settings.components.SettingTopBar

@Composable
fun FaveSettingScreen(
    navController: NavHostController,
    uiState: SettingsUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SettingTopBar(
                themeType = uiState.themeType,
                text = stringResource(id = R.string.my_fave_set_member),
                onArrowClick = {
                    navController.popBackStack()
                }
            )
        }

        var selected by remember { mutableStateOf(uiState.fave) }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            items(uiState.allMembers) { member ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            selected = member
                        }
                        .border(
                            width = 1.dp,
                            color = if (selected == member) uiState.themeType.subColor else Color.Gray.copy(alpha = 0.3f),
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CircleCheckbox(
                        selected = selected == member,
                        selectedTint = uiState.themeType.subColor,
                        onClick = {
                            selected = member
                        }
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    AsyncImage(
                        modifier = Modifier
                            .size(48.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray.copy(alpha = 0.3f),
                            ),
                        model = member.imgUrl,
                        contentDescription = "picture of ${member.name}",
                        contentScale = ContentScale.Crop,
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = member.name,
                        fontSize = 14.sp,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier
                    .background(uiState.themeType.subColor)
                    .padding(horizontal = 64.dp, vertical = 4.dp),
                text = stringResource(id = R.string.my_fave_confirm),
                fontSize = 18.sp,
                color = uiState.themeType.fontColor,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun CircleCheckbox(
    selected: Boolean,
    selectedTint: Color,
    onClick: () -> Unit = {},
) {
    val imageVector = Icons.Filled.CheckCircle
    val tint = if (selected) selectedTint else Color.White.copy(alpha = 0.8f)

    IconButton(
        onClick = onClick,
        modifier = Modifier.offset(x = 4.dp, y = 4.dp),
        enabled = !selected,
    ) {
        Icon(
            imageVector = imageVector, tint = tint,
            modifier = Modifier
                .background(Color.White, shape = CircleShape)
                .border(
                    width = 1.dp,
                    color = Color.Gray.copy(0.3f),
                    shape = CircleShape,
                ),
            contentDescription = "checkbox",
        )
    }
}

@Preview
@Composable
fun FaveSettingScreenPreview() {
    val navController = NavHostController(LocalContext.current)

    val member = Member(
        name = "坂道 太郎",
        imgUrl = "https://avatars.githubusercontent.com/u/52474650?v=4",
    )
    val all = listOf(
        Member(
            name = "坂道 太郎",
            imgUrl = "https://avatars.githubusercontent.com/u/52474650?v=4",
        ),
        Member(
            name = "坂道 二郎",
            imgUrl = "https://avatars.githubusercontent.com/u/52474650?v=4",
        ),
    )
    val uiState = SettingsUiState(
        fave = member,
        allMembers = all,
    )

    CustomSakaTheme {
        FaveSettingScreen(
            navController,
            uiState,
        )
    }
}
