package jp.mydns.kokoichi0206.sakamichiapp.presentation.util

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.sakamichiapp.di.AppModule
import jp.mydns.kokoichi0206.sakamichiapp.presentation.MainActivity
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class BottomNavigationBarTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @RelaxedMockK
    lateinit var navController: NavController

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)

        composeRule.activity.setContent {
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
                themeType = "",
            )
        }
    }

    @Test
    fun bottomBarIcons_displayAndCanTap() {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Blog,
            BottomNavItem.Position,
            BottomNavItem.Quiz,
            BottomNavItem.Setting,
        )
        items.forEach {
            composeRule.onNodeWithContentDescription("${it.name}").assertExists()
            composeRule.onNodeWithContentDescription("${it.name}").assertHasClickAction()
        }
    }

    @Test
    fun bottomBarIcon_navigateCorrectly() {
        // Arrange
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Blog,
            BottomNavItem.Position,
            BottomNavItem.Quiz,
            BottomNavItem.Setting,
        )
        val targetIcon = BottomNavItem.Blog
        items.forEach {
            verify(exactly = 0) {
                navController.navigate(it.route)
            }
        }

        // Act
        composeRule
            .onNodeWithContentDescription("${targetIcon.name}")
            .performClick()

        // Assert
        // The navigation of target icon is called only once.
        verify(exactly = 1) {
            navController.navigate(targetIcon.route)
        }
        // The navigation of other than target icon is never called.
        items.filter { it.name != targetIcon.name }
            .forEach {
                verify(exactly = 0) {
                    navController.navigate(it.route)
                }
            }
    }

    @Test
    fun bottomBarIcons_navigateCorrectly() {
        // Arrange
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.Blog,
            BottomNavItem.Position,
            BottomNavItem.Quiz,
            BottomNavItem.Setting,
        )

        // Act and Assert (not good ?)
        items.forEach {
            verify(exactly = 0) {
                navController.navigate(it.route)
            }
            composeRule
                .onNodeWithContentDescription("${it.name}")
                .performClick()
            verify {
                navController.navigate(
                    it.route
                )
            }
        }
    }
}
