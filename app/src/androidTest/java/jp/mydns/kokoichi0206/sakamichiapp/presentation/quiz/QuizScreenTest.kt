package jp.mydns.kokoichi0206.sakamichiapp.presentation.quiz

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.common.GroupName
import jp.mydns.kokoichi0206.data.di.DataModule
import jp.mydns.kokoichi0206.quiz.QuizScreen
import jp.mydns.kokoichi0206.quiz.QuizType
import jp.mydns.kokoichi0206.quiz.TestTags
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
@UninstallModules(AppModule::class, DataModule::class)
class QuizScreenTest {

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
    fun chooseGroupNames_display() {
        // Arrange
        val groupNames = GroupName.values()

        // Act

        // Assert
        groupNames.forEach { groupName ->
            composeRule
                .onNodeWithTag(groupName.name)
                .assertExists()
        }
    }

    @Test
    fun chooseGroupNames_showsCorrectText() {
        // Arrange
        val groupNames = GroupName.values()
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Act

        // Assert
        groupNames.forEach { groupName ->
            val expectedStr =
                context.resources.getString(R.string.choose_group_name_text, groupName.jname)
            composeRule
                .onNodeWithText(expectedStr)
                .assertExists()
        }
    }

    @Test
    fun chooseGroupNames_navigateToQuizTypePage() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedStr =
            context.resources.getString(R.string.choose_group_name_text, GroupName.NOGIZAKA.jname)

        // Act
        composeRule
            .onNodeWithText(expectedStr)
            .performClick()

        // Assert
        composeRule
            .onNodeWithTag(TestTags.CHOOSE_QUIZ_TYPE_PAGE_TITLE)
            .assertExists()
    }

    @Test
    fun quizTypePage_showsCorrectlyAfterClickedGroupNames() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedStr =
            context.resources.getString(R.string.choose_group_name_text, GroupName.NOGIZAKA.jname)

        // Act
        composeRule
            .onNodeWithText(expectedStr)
            .performClick()

        // Assert
        QuizType.values().forEach { quizType ->
            composeRule
                .onNodeWithText(quizType.jname)
                .assertExists()
        }
    }

    @Test
    fun quizTypePage_showsCorrectQuizTypes() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedStr =
            context.resources.getString(R.string.choose_group_name_text, GroupName.NOGIZAKA.jname)

        // Act
        composeRule
            .onNodeWithText(expectedStr)
            .performClick()

        // Assert
        QuizType.values().forEachIndexed { index, quizType ->
            composeRule
                .onAllNodesWithTag(TestTags.QUIZ_TYPE_CHOICE)[index]
                .assertExists()
                .assertTextEquals(quizType.jname)
        }
    }
}