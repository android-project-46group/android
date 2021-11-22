package io.kokoichi.sample.sakamichiapp.presentation.blog

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class BlogScreenTest {

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

        composeRule.setContent {
            BlogScreenWithCustomTheme(
                navController = navController,
            )
        }
    }

    @Test
    fun blogScreen_displayCorrectly() {
        // Arrange

        // Act

        // Assert
        composeRule.onNodeWithTag(TestTags.BLOG_TITLE).assertExists()
        composeRule.onNodeWithTag(TestTags.BLOG_SORT_BUTTON).assertExists()
        composeRule.onNodeWithTag(TestTags.GROUP_BAR).assertExists()
        composeRule.onAllNodesWithTag(TestTags.BLOG_ONE_BOX)[0].assertExists()
    }

    @Test
    fun blogScreenTitle_correctText() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val titleStr = context.resources.getString(R.string.blog_screen_title)

        // Act

        // Assert
        composeRule.onNodeWithTag(TestTags.BLOG_TITLE).assertTextEquals(titleStr)
    }
}
