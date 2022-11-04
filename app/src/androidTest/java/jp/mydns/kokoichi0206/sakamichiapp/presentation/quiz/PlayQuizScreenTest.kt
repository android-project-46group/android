package jp.mydns.kokoichi0206.sakamichiapp.presentation.quiz

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.quiz.QuizScreen
import jp.mydns.kokoichi0206.quiz.QuizType
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.di.AppModule
import jp.mydns.kokoichi0206.sakamichiapp.presentation.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class PlayQuizScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        composeRule.activity.setContent {
            QuizScreen()
        }
    }

    @Test
    fun playPage_displayCorrectParts() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedStr = context.resources.getString(
            R.string.choose_group_name_text,
            GroupName.values()[0].jname
        )
        composeRule
            .onNodeWithText(expectedStr)
            .performClick()
        val quizType = QuizType.values()[1].jname
        composeRule
            .onNodeWithText(quizType)
            .performClick()

        // Act

        // Assert
        // Check the Logcat (Debug , "TAG")
        composeRule.onRoot().printToLog("TAG")
        composeRule
            .onNodeWithTag(TestTags.PLAY_QUIZ_TITLE)
            .assertExists()
        composeRule
            .onAllNodesWithTag(TestTags.PLAY_QUIZ_ONE_CHOICE)[0]
            .assertExists()
        composeRule
            .onNodeWithTag(TestTags.PLAY_QUIZ_PROGRESS_BAR)
            .assertExists()
    }

    @Test
    fun playPlayBeforeTappingAns_notDisplayCircleOrClose() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedStr = context.resources.getString(
            R.string.choose_group_name_text,
            GroupName.values()[0].jname
        )
        composeRule
            .onNodeWithText(expectedStr)
            .performClick()
        val quizType = QuizType.values()[1].jname
        composeRule
            .onNodeWithText(quizType)
            .performClick()

        // Act

        // Assert
        composeRule
            .onNodeWithTag(TestTags.WRONG_CORRECT_ICON)
            .assertDoesNotExist()
    }

    @Test
    fun playPageWhenTapAns_displayCircleOrClose() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedStr = context.resources.getString(
            R.string.choose_group_name_text,
            GroupName.values()[0].jname
        )
        composeRule
            .onNodeWithText(expectedStr)
            .performClick()
        val quizType = QuizType.values()[1].jname
        composeRule
            .onNodeWithText(quizType)
            .performClick()

        // Act
        composeRule
            .onAllNodesWithTag(TestTags.PLAY_QUIZ_ONE_CHOICE)[0]
            .performClick()

        // Assert
        composeRule
            .onNodeWithTag(TestTags.WRONG_CORRECT_ICON)
            .assertExists()
    }
}