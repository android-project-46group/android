package io.kokoichi.sample.sakamichiapp.presentation.setting.pages

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.di.AppModule
import io.kokoichi.sample.sakamichiapp.presentation.MainActivity
import io.kokoichi.sample.sakamichiapp.presentation.setting.SettingsViewModel
import io.kokoichi.sample.sakamichiapp.presentation.setting.ThemeType
import io.kokoichi.sample.sakamichiapp.presentation.util.TestTags
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class SetThemeScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @RelaxedMockK
    lateinit var navController: NavHostController

    @RelaxedMockK
    lateinit var viewModel: SettingsViewModel

    lateinit var context: Context

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)

        composeRule.setContent {
            context = LocalContext.current
            SetThemeScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
    }

    @Test
    fun setThemeScreen_display() {
        // Arrange
        val expectedStr =
            context.resources.getString(R.string.set_theme_title)

        // Act

        // Assert
        composeRule
            .onNodeWithTag(TestTags.SET_THEME_TITLE)
            .assertExists()
            .assertTextEquals(expectedStr)
        composeRule
            .onNodeWithTag(TestTags.SET_THEME_BACK_ARROW)
            .assertExists()
        // Only one Check Icon
        composeRule
            .onNodeWithContentDescription("check")
            .assertExists()
        // At least one theme row
        composeRule
            .onAllNodesWithTag(TestTags.SET_THEME_THEME)[0]
            .assertExists()
    }

    @Test
    fun onBackArrowClicked_navigatePopBackStack() {
        // Arrange
        verify(exactly = 0) {
            navController.popBackStack()
        }

        // Act
        composeRule
            .onNodeWithTag(TestTags.SET_THEME_BACK_ARROW)
            .performClick()

        // Assert
        verify(exactly = 1) {
            navController.popBackStack()
        }
    }

    @Test
    fun onOtherThanBackArrowClicked_notNavigate() {
        // Arrange
        verify(exactly = 0) {
            navController.navigateUp()
            navController.popBackStack()
        }

        // Act
        composeRule
            .onNodeWithTag(TestTags.SET_THEME_TITLE)
            .performClick()
        composeRule
            .onAllNodesWithTag(TestTags.SET_THEME_THEME)[0]
            .performClick()
        composeRule
            .onNodeWithContentDescription("check")
            .assertExists()

        // Assert
        verify(exactly = 0) {
            navController.navigateUp()
            navController.popBackStack()
        }
    }

    @Test
    fun onThemeClicked_setTheme() {
        // Arrange
        val theme = ThemeType.Sakurazaka
        verify(exactly = 0) {
            viewModel.setThemeType(theme)
            viewModel.writeTheme(context, theme.name)
        }

        // Act
        composeRule
            .onNodeWithText(theme.name)
            .performClick()

        // Assert
        verify(exactly = 1) {
            viewModel.setThemeType(theme)
            viewModel.writeTheme(context, theme.name)
        }
    }
}
