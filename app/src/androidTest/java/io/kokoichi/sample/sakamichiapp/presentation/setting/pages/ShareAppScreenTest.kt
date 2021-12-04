package io.kokoichi.sample.sakamichiapp.presentation.setting.pages

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.di.AppModule
import io.kokoichi.sample.sakamichiapp.presentation.MainActivity
import io.kokoichi.sample.sakamichiapp.presentation.setting.SettingsUiState
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
class ShareAppScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @RelaxedMockK
    lateinit var navController: NavHostController

    @RelaxedMockK
    lateinit var uiState: SettingsUiState

    lateinit var context: Context

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)

        composeRule.setContent {
            context = LocalContext.current
            ShareAppScreen(
                navController = navController,
                uiState = uiState,
            )
        }
    }

    @Test
    fun shareAppScreen_display() {
        // Arrange
        val expectedTitleStr =
            context.resources.getString(R.string.share_app_title)
        val expectedMessageStr =
            context.resources.getString(R.string.share_app_message)

        // Act

        // Assert
        // Title in top bar
        composeRule
            .onNodeWithTag(TestTags.SETTING_TOP_BAR)
            .assertExists()
            .assertTextEquals(expectedTitleStr)
        // Image
        composeRule
            .onNodeWithTag(TestTags.SHARE_APP_QR_CODE)
            .assertExists()
        // Share with other apps button
        composeRule
            .onNodeWithTag(TestTags.SHARE_APP_MESSAGE)
            .assertExists()
            .assertTextEquals(expectedMessageStr)
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
}
