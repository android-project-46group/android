package jp.mydns.kokoichi0206.sakamichiapp.presentation.util

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import jp.mydns.kokoichi0206.common.datamanager.DataStoreManager
import kotlinx.coroutines.async

/**
 * Compose navigation set-up.
 */
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val navController = rememberNavController()

    val context = LocalContext.current

    // Theme type shared globally
    var themeType by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        // Read
        val storeVal = async {
            DataStoreManager.readString(context, DataStoreManager.KEY_THEME_GROUP)
        }
        themeType = storeVal.await()
    }

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
                    ),
                    themeType = themeType,
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding())
            ) {
                BottomNavHost(
                    navHostController = navController,
                ) {
                    themeType = it
                }
            }
        }
    }
}
