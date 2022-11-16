package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.pages

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.di.AppModule
import jp.mydns.kokoichi0206.sakamichiapp.presentation.MainActivity
import jp.mydns.kokoichi0206.settings.SettingsUiState
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import jp.mydns.kokoichi0206.data.di.DataModule
import jp.mydns.kokoichi0206.settings.TestTags
import jp.mydns.kokoichi0206.settings.pages.CacheClearDialog
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class, DataModule::class)
class CacheClearDialogTest {

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

        composeRule.activity.setContent {
            CacheClearDialog(
                navController = navController,
                uiState = uiState,
            )
        }
    }

    @Test
    fun dialog_displayCorrectText() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Act

        // Assert
        composeRule
            .onNodeWithTag(TestTags.CACHE_CLEAR_DIALOG_TITLE)
            .assertTextEquals(context.resources.getString(R.string.clear_cache_title))
        composeRule
            .onNodeWithTag(TestTags.CACHE_CLEAR_DIALOG_BODY)
            .assertTextEquals(context.resources.getString(R.string.clear_cache_body))
        composeRule
            .onNodeWithTag(TestTags.CACHE_CLEAR_DIALOG_CANCEL)
            .assertTextEquals(context.resources.getString(R.string.clear_cache_cancel))
        composeRule
            .onNodeWithTag(TestTags.CACHE_CLEAR_DIALOG_OK)
            .assertTextEquals(context.resources.getString(R.string.clear_cache_ok))

    }

    @Test
    fun okButton_navigateUp() {
        // Arrange

        // Act
        composeRule
            .onNodeWithTag(TestTags.CACHE_CLEAR_DIALOG_OK)
            .performClick()

        // Assert
        verify {
            navController.navigateUp()
        }
        composeRule
            .onNodeWithTag(TestTags.CACHE_CLEAR_DIALOG_TITLE)
            .assertDoesNotExist()
    }

    @Test
    fun cancelButton_navigateUp() {
        // Arrange

        // Act
        composeRule
            .onNodeWithTag(TestTags.CACHE_CLEAR_DIALOG_OK)
            .performClick()

        // Assert
        verify {
            navController.navigateUp()
        }
        composeRule
            .onNodeWithTag(TestTags.CACHE_CLEAR_DIALOG_TITLE)
            .assertDoesNotExist()
    }
}
