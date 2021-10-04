package io.kokoichi.sample.sakamichiapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.kokoichi.sample.sakamichiapp.*


@Composable
fun MainView(groupName: String, navController: NavHostController, viewModel: HomeViewModel) {


    val uiState by viewModel.uiState.collectAsState()
    Column {


        val BORDER_COLOR = Color.Gray
        val BORDER_THICKNESS = 2.dp



        // 通信が終わったことを通知するための変数
        var isDownloaded by remember { mutableStateOf(gIsDownloaded) }

        var selectedGroupNames by remember { mutableStateOf("sakurazaka") }
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