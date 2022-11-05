package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavController
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.di.AppModule
import jp.mydns.kokoichi0206.sakamichiapp.presentation.MainActivity
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import jp.mydns.kokoichi0206.settings.SettingNavigation
import jp.mydns.kokoichi0206.settings.SettingTopScreen
import jp.mydns.kokoichi0206.settings.SettingsUiState
import jp.mydns.kokoichi0206.settings.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class SettingTopScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @RelaxedMockK
    lateinit var navController: NavController

    @RelaxedMockK
    lateinit var uiState: SettingsUiState

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)

        val navigation = listOf(
            SettingNavigation.ClearCache
        )
        composeRule.activity.setContent {
            SettingTopScreen(
                navController = navController,
                navigationList = navigation,
                viewModel = mockk(),
                uiState = uiState,
            )
        }
    }

    @Test
    fun settingTitle_displayCorrectText() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedStr =
            context.resources.getString(R.string.setting_screen_title)

        // Act

        // Assert
        composeRule
            .onNodeWithTag(TestTags.SETTING_TITLE)
            .assertTextEquals(expectedStr)
    }

    @Test
    fun settingRow_display() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Act

        // Assert
        composeRule
            .onNodeWithTag(context.getString(SettingNavigation.ClearCache.name))
            .assertExists()
        composeRule
            .onNodeWithContentDescription("right arrow")
            .assertExists()
    }

    @Test
    fun settingRow_canNavigate() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        verify(exactly = 0) {
            navController.navigate(SettingNavigation.ClearCache.route)
        }

        // Act
        composeRule
            .onNodeWithText(context.getString(SettingNavigation.ClearCache.name))
            .performClick()

        // Assert
        verify(exactly = 1) {
            navController.navigate(SettingNavigation.ClearCache.route)
        }
    }
}
