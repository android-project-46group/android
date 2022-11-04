package jp.mydns.kokoichi0206.sakamichiapp.presentation.blog

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavController
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.di.AppModule
import jp.mydns.kokoichi0206.sakamichiapp.presentation.MainActivity
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import jp.mydns.kokoichi0206.blog.BlogScreenWithCustomTheme
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

        composeRule.activity.setContent {
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

    @Test
    fun swipeToRefresh_callAPI() {
        // Arrange
        val targetMember = "秋元 真夏"
        composeRule.onNodeWithText("$targetMember 0").assertExists()

        // Act
        // Swipe Down to refresh
        composeRule.onRoot().performTouchInput {
            android.os.SystemClock.sleep(1000)
            swipeDown(
                startY = centerY
            )
            android.os.SystemClock.sleep(1000)
        }

        // Assert
        // FIXME: Swipe actually calls API but increments the counter 2!!
        composeRule.onNodeWithText("$targetMember 2").assertExists()
    }
}
