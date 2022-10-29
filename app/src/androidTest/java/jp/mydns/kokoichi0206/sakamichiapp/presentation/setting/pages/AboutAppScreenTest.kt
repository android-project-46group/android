package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.pages

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertTextContains
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
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.SettingsUiState
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.TestTags
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import jp.mydns.kokoichi0206.sakamichiapp.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class AboutAppScreenTest {
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
            AboutAppScreen(
                navController = navController,
                uiState = uiState,
            )
        }
    }

    @Test
    fun aboutAppScreen_display() {
        // Arrange
        uiState = SettingsUiState(
            version = "1.1.9",
            userId = "1AAAAAAA-BBBB-CCCC-DDDD-3EEEEEEEEEEE"
        )

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedTitleStr = context.resources.getString(
            R.string.setting_about_app,
        )
        val expectedVersionTitle = context.resources.getString(
            R.string.about_app_version,
        )
        val expectedUserIdTitle = context.resources.getString(
            R.string.about_app_user_id,
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
            .onNodeWithTag(TestTags.ABOUT_APP_APP_ICON)
            .assertExists()
        // Version
        composeRule
            .onNodeWithTag(TestTags.ABOUT_APP_VERSION_TITLE)
            .assertExists()
            .assertTextEquals(expectedVersionTitle)
        composeRule
            .onNodeWithTag(TestTags.ABOUT_APP_VERSION_CONTENT)
            .assertExists()
            .assertTextContains("1.1.9")

        // User ID
        composeRule
            .onNodeWithTag(TestTags.ABOUT_APP_USERID_TITLE)
            .assertExists()
            .assertTextEquals(expectedUserIdTitle)
        composeRule
            .onNodeWithTag(TestTags.ABOUT_APP_USERID_CONTENT)
            .assertExists()
            .assertTextContains("1AAAAAAA-BBBB-CCCC-DDDD-3EEEEEEEEEEE")
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
