package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.CustomSakaTheme

/**
 * Function to display member detail page.
 */
@Composable
fun MemberDetailScreen(
    member: jp.mydns.kokoichi0206.model.Member,
    viewModel: MemberDetailViewModel = hiltViewModel()
) {
    viewModel.setMember(member = member)
    viewModel.setTags(tags = listOf("かわいい", member.generation))
    val uiState by viewModel.uiState.collectAsState()

    CustomSakaTheme(group = member.group!!) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            Column() {
                Box(modifier = Modifier.weight(1f)) {
                    MemberImage(uiState = uiState)
                }
                Box(modifier = Modifier.weight(1f)) {
                    Infos(uiState = uiState)
                }
            }
        }
    }
}
