package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.pages

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.sakamichiapp.di.AppModule
import jp.mydns.kokoichi0206.sakamichiapp.presentation.MainActivity
import jp.mydns.kokoichi0206.settings.SettingsUiState
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import jp.mydns.kokoichi0206.data.di.DataModule
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.settings.TestTags
import jp.mydns.kokoichi0206.settings.pages.ShareAppScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class, DataModule::class)
class ShareAppScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @RelaxedMockK
    lateinit var navController: NavHostController

    @RelaxedMockK
    lateinit var uiState: SettingsUiState

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)

        composeRule.activity.setContent {
            ShareAppScreen(
                navController = navController,
                uiState = uiState,
            )
        }
    }

    @Test
    fun shareAppScreen_display() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedTitleStr = context.resources.getString(
            R.string.share_app_title,
        )
        val expectedMessageStr = context.resources.getString(
            R.string.share_app_message,
        )

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
