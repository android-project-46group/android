package jp.mydns.kokoichi0206.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import jp.mydns.kokoichi0206.settings.ThemeType
import jp.mydns.kokoichi0206.common.ui.theme.IconSizeMedium
import jp.mydns.kokoichi0206.common.ui.theme.SpaceMedium
import jp.mydns.kokoichi0206.common.ui.theme.SpaceTiny
import jp.mydns.kokoichi0206.common.ui.theme.Typography
import jp.mydns.kokoichi0206.feature.settings.R
import jp.mydns.kokoichi0206.settings.TestTags

@Composable
fun SettingTopBar(
    themeType: ThemeType,
    text: String,
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
                .testTag(TestTags.SETTING_TOP_BAR),
            text = text,
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