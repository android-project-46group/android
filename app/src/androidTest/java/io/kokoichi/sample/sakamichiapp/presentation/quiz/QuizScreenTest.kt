package io.kokoichi.sample.sakamichiapp.presentation.quiz

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.kokoichi.sample.sakamichiapp.R
import io.kokoichi.sample.sakamichiapp.di.AppModule
import io.kokoichi.sample.sakamichiapp.presentation.MainActivity
import io.kokoichi.sample.sakamichiapp.presentation.util.TestTags
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class QuizScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        composeRule.setContent {
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