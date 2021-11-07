package io.kokoichi.sample.sakamichiapp.presentation.member_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import io.kokoichi.sample.sakamichiapp.R

/**
 * Member Image
 */
@Composable
fun MemberImage(
    uiState: MemberDetailUiState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 60.dp)
    ) {
        Image(
            painter = rememberImagePainter(uiState.member?.imgUrl,
                builder = {
                    placeholder(
                        when (uiState.member?.group) {
                            "乃木坂" ->
                                R.drawable.nogizaka_official_icon
                            "櫻坂" ->
                                R.drawable.sakurazaka_official_icon
                            else ->
                                R.drawable.hinata_official_icon
                        }
                    )
                    listener(
                        onStart = {
                            // set your progressbar visible here
                        },
                        onSuccess = { request, metadata ->
                            // set your progressbar invisible here
                        }
                    )
                }),
            contentDescription = "image of ${uiState.member!!.name}",
            contentScale = ContentScale.Crop, // crop the image if it's not a square
            modifier = Modifier
                .size(350.dp),
        )
    }
}