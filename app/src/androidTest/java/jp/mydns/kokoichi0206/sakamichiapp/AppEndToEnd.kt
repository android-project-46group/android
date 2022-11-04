package jp.mydns.kokoichi0206.sakamichiapp

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.sakamichiapp.di.AppModule
import jp.mydns.kokoichi0206.sakamichiapp.presentation.MainActivity
import jp.mydns.kokoichi0206.settings.SettingNavigation
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.BottomNavItem
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Navigation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests as close to realities as possible.
 */
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class AppEndToEnd {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()

        // Use real navigation (not mockk)
        composeRule.activity.setContent {
            Navigation()
        }
    }

    @Test
    fun onBackClickAfterNavigated_closeApp() {
        // Check if the only navigation is stocked.
        // Arrange
        val targetIcon = BottomNavItem.Setting

        composeRule
            .onNodeWithContentDescription("${targetIcon.name}")
            .assertExists()
            .performClick()

        // Act and Assert
        assertThrows(
            // Raise error because the app finishes for only one back click.
            androidx.test.espresso.NoActivityResumedException::class.java
        ) {
            Espresso.pressBack()
        }
    }

    @Test
    fun clickInSettingScreen_onlyOneNavigationStock() {
        // Arrange
        val settingIcon = BottomNavItem.Setting
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Move to SettingsScreen
        composeRule
            .onNodeWithContentDescription("${settingIcon.name}")
            .assertExists()
            .performClick()
        // Now you're SettingTopScreen
        composeRule
            .onNodeWithTag(TestTags.SETTING_TITLE)
            .assertExists()
        // Click different rows twice
        val row = SettingNavigation.ReportIssue
        composeRule
            .onNodeWithTag(context.getString(row.name))
            .assertExists()
            .performClick()
        composeRule
            .onNodeWithTag(TestTags.SETTING_TITLE)
            .assertDoesNotExist()

        // Act
        Espresso.pressBack()

        // Assert
        // Now you're back to SettingTopScreen
        composeRule
            .onNodeWithTag(TestTags.SETTING_TITLE)
            .assertExists()
    }
}