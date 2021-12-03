package io.kokoichi.sample.sakamichiapp.presentation.setting.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.presentation.setting.SettingsViewModel
import io.kokoichi.sample.sakamichiapp.presentation.setting.ThemeType
import io.kokoichi.sample.sakamichiapp.presentation.setting.themeTypes
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.*
import io.kokoichi.sample.sakamichiapp.presentation.util.TestTags

@Composable
fun SetThemeScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel,
    selected: String = ThemeType.BasicNight.name,
    onThemeChanged: (String) -> Unit = {},
) {
    var selectedItem by remember { mutableStateOf(selected) }

    val context = LocalContext.current

    Column {
        ThemeTopBar(
            themeType = themeTypes.first { it.name == selectedItem },
            onArrowClick = {
                navController.popBackStack()
            },
        )
        Spacer(modifier = Modifier.height(SpaceMedium))
        ThemeTypesColumn(
            selectedItem = selectedItem,
            colorTypes = themeTypes,
            onclick = { type ->
                selectedItem = type.name
                viewModel.setThemeType(type)
                viewModel.writeTheme(context, type.name)
                onThemeChanged(type.name)
            }
        )
    }
}

@Composable
fun ThemeTopBar(
    themeType: ThemeType,
    onArrowClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(themeType.subColor)
            .padding(SpaceMedium),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.SET_THEME_TITLE),
            text = stringResource(R.string.set_theme_title),
            style = Typography.h5,
            color = themeType.fontColor,
            textAlign = TextAlign.Center,
        )
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.back_arrow),
            tint = themeType.fontColor,
            modifier = Modifier
                .size(
                    width = IconSizeMedium + SpaceTiny + SpaceTiny,
                    height = IconSizeMedium + SpaceTiny
                )
                .clip(RoundedCornerShape(SpaceTiny))
                .clickable {
                    onArrowClick()
                }
                .padding(end = SpaceTiny)
                .padding(SpaceTiny / 2)
                .testTag(TestTags.SET_THEME_BACK_ARROW),
            )

    }
}

@Composable
fun ThemeTypesColumn(
    selectedItem: String,
    colorTypes: List<ThemeType>,
    onclick: (ThemeType) -> Unit = {},
) {
    LazyColumn {
        items(colorTypes) { type ->
            ColorRow(
                type = type,
                isSelected = type.name == selectedItem,
                onclick = {
                    onclick(type)
                },
            )
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
            )
        }
    }
}

@Composable
fun ColorRow(
    type: ThemeType,
    isSelected: Boolean = false,
    onclick: (ThemeType) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onclick(type)
            }
            .padding(SpaceMedium)
            .testTag(TestTags.SET_THEME_THEME),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(SpaceSmall))
        Box(
            modifier = Modifier
                .size(IconSizeMedium)
                .clip(CircleShape)
                .background(type.subColor)
        )
        Spacer(modifier = Modifier.width(SpaceLarge))
        Text(
            text = type.name,
            style = Typography.body1,
            color = if (isSelected) {
                Color.Black
            } else {
                Color.LightGray
            },
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(R.string.check),
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(IconSizeMedium),
                )
            }
        }
    }
}
