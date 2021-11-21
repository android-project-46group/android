package io.kokoichi.sample.sakamichiapp.presentation.setting

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.kokoichi.sample.sakamichiapp.di.AppModule
import io.kokoichi.sample.sakamichiapp.presentation.MainActivity
import io.kokoichi.sample.sakamichiapp.presentation.util.TestTags
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class UpdateBlogScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @RelaxedMockK
    lateinit var navController: NavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this)

        composeRule.setContent {
            UpdateBlogScreen(
                navController = navController,
                viewModel = mockk()
            )
        }
    }

    @Test
    fun contents_display() {
        // Arrange

        // Act

        // Assert
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_TITLE)
            .assertExists()
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_BODY)
            .assertExists()
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_OK_BUTTON)
            .assertExists()
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_CANCEL_BUTTON)
            .assertExists()
    }

    @Test
    fun onOkButtonClicked_openDialog() {
        // Arrange
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_DIALOG_TITLE)
            .assertDoesNotExist()
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_DIALOG_BODY)
            .assertDoesNotExist()
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_DIALOG_OK_BUTTON)
            .assertDoesNotExist()

        // Act
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_OK_BUTTON)
            .performClick()

        // Assert
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_DIALOG_TITLE)
            .assertExists()
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_DIALOG_BODY)
            .assertExists()
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_DIALOG_OK_BUTTON)
            .assertExists()
    }

    @Test
    fun onCancelButtonClicked_navigatePopBackStack() {
        // Arrange
        verify(exactly = 0) {
            navController.popBackStack()
        }

        // Act
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_CANCEL_BUTTON)
            .performClick()

        // Assert
        verify(exactly = 1) {
            navController.popBackStack()
        }
    }

    @Test
    fun onDialogOkButtonClicked_closeDialog() {
        // Arrange
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_OK_BUTTON)
            .performClick()
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_DIALOG_TITLE)
            .assertExists()

        // Act
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_DIALOG_OK_BUTTON)
            .performClick()

        // Assert
        composeRule
            .onNodeWithTag(TestTags.UPDATE_BLOG_DIALOG_TITLE)
            .assertDoesNotExist()
    }
}
