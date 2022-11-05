package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.pages

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.di.AppModule
import jp.mydns.kokoichi0206.sakamichiapp.presentation.MainActivity
import jp.mydns.kokoichi0206.settings.SettingsUiState
import jp.mydns.kokoichi0206.settings.SettingsViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import jp.mydns.kokoichi0206.settings.TestTags
import jp.mydns.kokoichi0206.settings.pages.ReportIssueScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class ReportIssueScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @RelaxedMockK
    lateinit var viewModel: SettingsViewModel

    @RelaxedMockK
    lateinit var uiState: SettingsUiState

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)

        composeRule.activity.setContent {
            ReportIssueScreen(
                viewModel = viewModel,
                uiState = uiState,
            )
        }
    }

    @Test
    fun reportIssue_display() {
        // Arrange

        // Act

        // Assert
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_TITLE)
            .assertExists()
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BODY)
            .assertExists()
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BUTTON)
            .assertExists()
    }

    @Test
    fun bodyAtFirst_displayPlaceholder() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expected = context.resources.getString(R.string.report_issue_text_placeholder)

        // Act

        // Assert
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BODY)
            .assertTextContains(expected)
    }

    @Test
    fun body_canEnter() {
        // Arrange
        val inputStr = "テストが失敗しました"

        // Act
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BODY)
            .performTextInput(inputStr)

        // Assert
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BODY)
            .assertTextContains(inputStr)
    }

    @Test
    fun onButtonClickWithoutMessage_doesNotOpenDialog() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expected = context.resources.getString(R.string.report_issue_text_placeholder)
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BODY)
            .assertTextContains(expected)
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_TITLE)
            .assertDoesNotExist()

        // Act
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BUTTON)
            .performClick()

        // Assert
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_TITLE)
            .assertDoesNotExist()
    }

    @Test
    fun onButtonClickWithMessage_doesNotOpenDialog() {
        // Arrange
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BODY)
            .performTextInput("Some Text Here")
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_TITLE)
            .assertDoesNotExist()

        // Act
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BUTTON)
            .performClick()

        // Assert
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_TITLE)
            .assertExists()
    }

    @Test
    fun dialog_display() {
        // Arrange
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BODY)
            .performTextInput("Some Text Here")
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BUTTON)
            .performClick()

        // Act

        // Assert
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_TITLE)
            .assertExists()
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_BODY)
            .assertExists()
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_OK)
            .assertExists()
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_CANCEL)
            .assertExists()
    }

    @Test
    fun dialogCancelButtonClicked_doesNotSend() {
        // Arrange
        val sendMessage = "Some Text Here"
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BODY)
            .performTextInput(sendMessage)
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BUTTON)
            .performClick()
        verify(exactly = 0) {
            viewModel.reportIssue(sendMessage)
        }

        // Act
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_CANCEL)
            .performClick()

        // Assert
        verify(exactly = 0) {
            viewModel.reportIssue(sendMessage)
        }
    }

    @Test
    fun dialogOkButtonClicked_sendTextCorrectly() {
        // Arrange
        val sendMessage = "Some Text Here"
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BODY)
            .performTextInput(sendMessage)
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BUTTON)
            .performClick()
        verify(exactly = 0) {
            viewModel.reportIssue(sendMessage)
        }

        // Act
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_OK)
            .performClick()

        // Assert
        verify(exactly = 1) {
            viewModel.reportIssue(sendMessage)
        }
    }

    @Test
    fun dialogOkButtonClicked_closeDialog() {
        // Arrange
        val sendMessage = "Some Text Here"
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BODY)
            .performTextInput(sendMessage)
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BUTTON)
            .performClick()
        // Check the dialog is not showing.
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_TITLE)
            .assertExists()

        // Act
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_OK)
            .performClick()

        // Assert
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_TITLE)
            .assertDoesNotExist()
    }

    @Test
    fun dialogCancelButtonClicked_closeDialog() {
        // Arrange
        val sendMessage = "Some Text Here"
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BODY)
            .performTextInput(sendMessage)
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_BUTTON)
            .performClick()
        // Check the dialog is not showing.
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_TITLE)
            .assertExists()

        // Act
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_CANCEL)
            .performClick()

        // Assert
        composeRule
            .onNodeWithTag(TestTags.REPORT_ISSUE_DIALOG_TITLE)
            .assertDoesNotExist()
    }
}
