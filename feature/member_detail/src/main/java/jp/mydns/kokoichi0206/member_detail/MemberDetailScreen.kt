package jp.mydns.kokoichi0206.member_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.mydns.kokoichi0206.common.GroupName
import jp.mydns.kokoichi0206.common.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.model.Member

/**
 * Function to display member detail page.
 */
@Composable
fun MemberDetailScreen(
    member: Member,
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.size(400.dp)) {
            MemberImage(uiState = uiState)
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Infos(uiState = uiState)
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
