package io.kokoichi.sample.sakamichiapp.presentation.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

/**
 * Compose navigation set-up.
 */
@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    modifier = Modifier.height(56.dp),
                    navController = navController,
                    items = listOf(
                        BottomNavItem.Home,
                        BottomNavItem.Blog,
                        BottomNavItem.Position,
                        BottomNavItem.Quiz,
                        BottomNavItem.Setting,
                    )
                )
            }) {
            BottomNavHost(navHostController = navController)
        }
    }
}
