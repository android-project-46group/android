package jp.mydns.kokoichi0206.blog.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.common.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.common.ui.theme.SpaceTiny
import jp.mydns.kokoichi0206.common.ui.theme.Typography

/**
 * Skeleton Screen
 * ref: https://mui.com/material-ui/react-skeleton/#variants
 */
@Composable
fun SkeletonBlogScreen() {
    LazyColumn(
        contentPadding = Constants.BottomBarPadding,
    ) {
        items(8) {
            Row {
                Box(modifier = Modifier.weight(1f))
                SkeletonPart(120.dp)
                Box(modifier = Modifier.weight(1f))
                SkeletonPart(120.dp)
                Box(modifier = Modifier.weight(1f))
                SkeletonPart(120.dp)
                Box(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun SkeletonPart(
    size: Dp,
) {
    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition()

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            )
        )
    )
    val brush = linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
    Column(
        modifier = Modifier
            .padding(vertical = SpaceSmall)
            .width(size),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            modifier = Modifier
                .blogImage()
                .background(brush),
        )

        Text(
            modifier = Modifier
                .padding(top = SpaceSmall)
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(brush),
            text = "name",
            style = Typography.body2,
            color = Color.Transparent,
        )
        Text(
            modifier = Modifier
                .padding(top = SpaceSmall)
                .padding(bottom = SpaceTiny)
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(brush),
            text = "time",
            style = Typography.caption,
            color = Color.Transparent,
        )
    }
}
