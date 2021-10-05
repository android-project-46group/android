package io.kokoichi.sample.sakamichiapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.navigation.NavHostController


@Composable
fun MainView(navController: NavHostController, viewModel: HomeViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    Column {

        GroupBar(
            uiState = uiState,
            viewModel = viewModel
        )

        SortBar(
            uiState = uiState,
            viewModel = viewModel,
        )

        MainColumn(
            uiState = uiState,
            viewModel = viewModel,
            navController = navController,
        )
    }
}