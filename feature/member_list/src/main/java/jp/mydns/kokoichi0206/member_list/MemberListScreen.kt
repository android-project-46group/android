package jp.mydns.kokoichi0206.member_list

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import jp.mydns.kokoichi0206.common.GroupNameInMemberList
import jp.mydns.kokoichi0206.common.components.GroupBarInMemberList
import jp.mydns.kokoichi0206.common.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.common.ui.theme.SpaceMedium
import jp.mydns.kokoichi0206.common.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.member_list.components.SkeletonMemberScreen
import jp.mydns.kokoichi0206.member_list.components.SortBar
import jp.mydns.kokoichi0206.model.Member
import kotlinx.coroutines.launch

/**
 * Function to display member list.
 */
@Composable
fun MemberListScreen(
    viewModel: MemberListViewModel = hiltViewModel(),
    onPersonClick: (Member) -> Unit = {},
) {
    // Initialize the members when first loaded.
    if (!viewModel.hasInitialized()) {
        viewModel.setApiMembers()
        viewModel.setHasInitialized(true)
    }

    val uiState by viewModel.uiState.collectAsState()
    CustomSakaTheme(group = uiState.groupName.jname) {
        MainView(
            uiState,
            onRefresh = {
                viewModel.setApiMembers(force = true)
            },
            onPersonClick,
            onGroupClicked = { gn ->
                viewModel.setGroupName(gn)
                viewModel.setApiMembers()
            },
            onSortClicked = { sortKey ->
                viewModel.setSortKey(sortKey)
                viewModel.sortMembers()
                // Change the visible(show) style
                viewModel.setVisibleStyle(sortKey)
            },
            onSortTypeClicked = {
                viewModel.setSortType(
                    when (uiState.sortType) {
                        SortOrderType.ASCENDING ->
                            SortOrderType.DESCENDING

                        SortOrderType.DESCENDING ->
                            SortOrderType.ASCENDING
                    }
                )
                // Notify viewModel to re-sort
                viewModel.sortMembers()
            },
            onNarrowClilcked = { nKey ->
                // narrow down the visible members
                viewModel.setNarrowType(NarrowKeys.valueOf(nKey.toString()))

                viewModel.narrowDownVisibleMembers(nKey)
            },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainView(
    uiState: MemberListUiState,
    onRefresh: () -> Unit = {},
    onPersonClick: (Member) -> Unit = {},
    onGroupClicked: (GroupNameInMemberList) -> Unit = {},
    onSortClicked: (MemberListSortKeys) -> Unit = {},
    onSortTypeClicked: () -> Unit = {},
    onNarrowClilcked: (NarrowKeys) -> Unit = {},
) {
    val tabItems = GroupNameInMemberList.values()
    val pagerState = rememberPagerState {
        tabItems.size
    }
    val animationScope = rememberCoroutineScope()
    LaunchedEffect(key1 = pagerState.currentPage) {
        onGroupClicked(tabItems[pagerState.currentPage])
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        GroupBarInMemberList(
            selectedGroupName = uiState.groupName,
            onclick = { gn ->
                animationScope.launch {
                    pagerState.animateScrollToPage(
                        tabItems.indexOf(gn)
                    )
                }
            },
            modifier = Modifier
                .padding(top = SpaceSmall),
            items = tabItems,
        )

        SortBar(
            uiState = uiState,
            onSortClicked = { sortKey ->
                onSortClicked(sortKey)
            },
            onSortTypeClicked = {
                onSortTypeClicked()
            },
            onNarrowClicked = {
                onNarrowClilcked(it)
            }
        )

        HorizontalPager(
            state = pagerState,
        ) { idx ->
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = uiState.isRefreshing),
                onRefresh = {
                    onRefresh()
                }
            ) {
                SwipableArea(
                    uiState = uiState,
                    onPersonClick = onPersonClick,
                )
            }
        }
    }
}

/**
 * An area to be swiped.
 * This area should be vertically scrollable.
 */
@Composable
fun SwipableArea(
    uiState: MemberListUiState,
    onPersonClick: (Member) -> Unit = {},
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.error) {
        if (uiState.error.isNotBlank()) {
            Toast.makeText(
                context,
                uiState.error,
                Toast.LENGTH_LONG,
            ).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = SpaceMedium),
    ) {
        if (uiState.isLoading) {
            // スケルトンスクリーン。
            SkeletonMemberScreen()
        } else {
            MainColumn(
                uiState = uiState,
                onPersonClick = onPersonClick,
            )
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    val uiState = MemberListUiState(
        visibleMembers = mutableListOf(
            Member(
                name = "名前0",
                birthday = "1835年1月10日",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/example/0.png",
            ),
            Member(
                name = "名前1",
                birthday = "1835年1月10日",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/example/1.png",
            ),
            Member(
                name = "名前2",
                birthday = "1835年1月10日",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/example/2.png",
            ),
            Member(
                name = "名前3",
                birthday = "1835年1月10日",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/example/3.png",
            ),
            Member(
                name = "名前4",
                birthday = "1835年1月10日",
                imgUrl = "https://kokoichi0206.mydns.jp/imgs/example/4.png",
            ),
        ),
        isLoading = false,
    )

    CustomSakaTheme(group = uiState.groupName.jname) {
        MainView(uiState = uiState)
    }
}

@Preview
@Composable
fun MainViewSkeletonPreview() {
    val uiState = MemberListUiState(
        isLoading = true,
    )

    CustomSakaTheme(group = uiState.groupName.jname) {
        MainView(uiState = uiState)
    }
}
