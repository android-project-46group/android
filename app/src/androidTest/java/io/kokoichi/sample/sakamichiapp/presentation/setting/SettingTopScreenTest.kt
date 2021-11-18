package io.kokoichi.sample.sakamichiapp.presentation.setting

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavController
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.di.AppModule
import io.kokoichi.sample.sakamichiapp.presentation.MainActivity
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
class SettingTopScreenTest {

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

        val navigation = listOf(
            SettingNavigation.ClearCache
        )
        composeRule.setContent {
            SettingTopScreen(
                navController = navController,
                navigationList = navigation,
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

        // Act

        // Assert
        composeRule
            .onNodeWithTag(SettingNavigation.ClearCache.name)
            .assertExists()
        composeRule
            .onNodeWithContentDescription("right arrow")
            .assertExists()
    }

    @Test
    fun settingRow_canNavigate() {
        // Arrange

        // Act
        composeRule
            .onNodeWithText(SettingNavigation.ClearCache.name)
            .performClick()

        // Assert
        verify {
            navController.navigate(SettingNavigation.ClearCache.route)
        }
    }
}
