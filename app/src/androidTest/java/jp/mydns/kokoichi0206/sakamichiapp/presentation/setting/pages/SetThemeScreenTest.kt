package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.pages

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.di.AppModule
import jp.mydns.kokoichi0206.sakamichiapp.presentation.MainActivity
import jp.mydns.kokoichi0206.settings.SettingsViewModel
import jp.mydns.kokoichi0206.settings.ThemeType
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import jp.mydns.kokoichi0206.data.di.DataModule
import jp.mydns.kokoichi0206.settings.TestTags
import jp.mydns.kokoichi0206.settings.pages.SetThemeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class, DataModule::class)
class SetThemeScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @RelaxedMockK
    lateinit var navController: NavHostController

    @RelaxedMockK
    lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)

        composeRule.activity.setContent {
            SetThemeScreen(
                navController = navController,
                setThemeType = {
                    viewModel.setThemeType(ThemeType.Sakurazaka)
                },
            )
        }
    }

    @Test
    fun setThemeScreen_display() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedStr = context.resources.getString(
            R.string.set_theme_title,
        )

        // Act

        // Assert
        composeRule
            .onNodeWithTag(TestTags.SETTING_TOP_BAR)
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
            .onNodeWithTag(TestTags.SETTING_TOP_BAR)
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
        }

        // Act
        composeRule
            .onNodeWithText(theme.name)
            .performClick()

        // Assert
        verify(exactly = 1) {
            viewModel.setThemeType(theme)
        }
    }
}
