package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list.components.SortBar
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceMedium
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceSmall
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list.components.SkeletonMemberScreen

/**
 * Function to display member list.
 */
@Composable
fun MemberListScreen(
    navController: NavController,
    viewModel: MemberListViewModel = hiltViewModel()
) {
    // Initialize the members when first loaded.
    if (!viewModel.hasInitialized()) {
        viewModel.setApiMembers()
        viewModel.setHasInitialized(true)
    }

    val uiState by viewModel.uiState.collectAsState()
    CustomSakaTheme(group = uiState.groupName.jname) {
        MainView(
            uiState, navController, viewModel
        )
    }
}

@Composable
fun MainView(
    uiState: MemberListUiState,
    navController: NavController,
    viewModel: MemberListViewModel
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        GroupBar(
            selectedGroupName = uiState.groupName,
            onclick = { gn ->
                viewModel.setGroupName(gn)
                viewModel.setApiMembers()
            },
            modifier = Modifier
                .padding(top = SpaceSmall)
        )

        SortBar(
            uiState = uiState,
            viewModel = viewModel,
        )

        SwipableArea(
            uiState = uiState,
            navController = navController,
        )
    }
}

/**
 * An area to be swiped.
 * This area should be vertically scrollable.
 */
@Composable
fun SwipableArea(
    uiState: MemberListUiState,
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = SpaceMedium),
        contentAlignment = Alignment.Center,
    ) {
        if (uiState.isLoading) {
            // スケルトンスクリーン。
            SkeletonMemberScreen()
        } else if (uiState.error.isNotBlank()) {
            Text(
                text = uiState.error,
                color = MaterialTheme.colors.primary,
            )
        } else {
            MainColumn(
                uiState = uiState,
                navController = navController,
            )
        }
    }
}
