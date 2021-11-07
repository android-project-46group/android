package io.kokoichi.sample.sakamichiapp.presentation.member_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.kokoichi.sample.sakamichiapp.presentation.member_list.components.SortBar
import io.kokoichi.sample.sakamichiapp.presentation.ui.theme.CustomSakaTheme

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

    // Change color of ActionBar using systemuicontroller.
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.White,
    )

    CustomSakaTheme(group = uiState.groupName) {
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
            uiState = uiState,
            viewModel = viewModel,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(bottom = 4.dp)
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
