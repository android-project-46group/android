package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list.components.SortBar
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.CustomSakaTheme
import jp.mydns.kokoichi0206.sakamichiapp.presentation.ui.theme.SpaceSmall

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

        MainColumn(
            uiState = uiState,
            navController = navController,
        )
    }
}
