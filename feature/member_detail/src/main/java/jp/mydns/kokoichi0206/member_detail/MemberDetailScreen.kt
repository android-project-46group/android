package jp.mydns.kokoichi0206.member_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import jp.mydns.kokoichi0206.common.GroupName
import jp.mydns.kokoichi0206.common.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.member_detail.Infos
import jp.mydns.kokoichi0206.member_detail.MemberDetailViewModel
import jp.mydns.kokoichi0206.member_detail.MemberImage
import jp.mydns.kokoichi0206.model.Member

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
        MainDetailView(uiState = uiState)
    }
}

@Composable
fun MainDetailView(
    uiState: MemberDetailUiState,
) {
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

@Preview
@Composable
fun MainDetailViewPreview() {
    val uiState = MemberDetailUiState(
        tags = mutableListOf("かわいい", "天才"),
        member = Member(
            bloodType = "A型",
            generation = "1期生",
            height = "212cm",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/example/0.png",
            name = "名前 A",
            birthday = "2001年8月29日",
            group = "乃木坂",
        )
    )
    CustomSakaTheme(group = GroupName.NOGIZAKA.jname) {
        MainDetailView(uiState = uiState)
    }
}

@Preview
@Composable
fun MainDetailViewWithSakuraPreview() {
    val uiState = MemberDetailUiState(
        tags = mutableListOf("かわいい", "天才"),
        member = Member(
            bloodType = "A型",
            generation = "2期生",
            height = "212cm",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/example/0.png",
            name = "名前 FK",
            birthday = "2001年8月29日",
            group = "櫻坂",
        )
    )
    CustomSakaTheme(group = GroupName.SAKURAZAKA.jname) {
        MainDetailView(uiState = uiState)
    }
}
