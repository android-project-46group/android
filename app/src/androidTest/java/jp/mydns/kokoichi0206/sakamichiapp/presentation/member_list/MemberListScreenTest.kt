package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.sakamichiapp.di.AppModule
import jp.mydns.kokoichi0206.sakamichiapp.presentation.MainActivity
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import jp.mydns.kokoichi0206.member_detail.navigation.memberDetailRoute
import jp.mydns.kokoichi0206.member_detail.navigation.memberJson
import jp.mydns.kokoichi0206.model.getJsonFromMember
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class MemberListScreenTest {

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
            jp.mydns.kokoichi0206.member_list.MemberListScreen {
                navController.navigateUp()
            }
        }
    }

    @Test
    fun memberImage_display() {
        // Members are defined in data/remote/MockSakamichiApi
        composeRule.onNodeWithContentDescription("image of 秋元 真夏 0").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("image of 生田 絵梨花 0").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("image of 伊藤 理々杏 0").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("image of 岩本 蓮加 0").assertIsDisplayed()
    }

    @Test
    fun memberImage_canTap() {
        // Members are defined in data/remote/MockSakamichiApi
        composeRule.onNodeWithContentDescription("image of 秋元 真夏 0").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("image of 秋元 真夏 0").assertHasClickAction()
    }

    @Test
    fun memberImage_canTapCorrectly() {
        // Arrange
        val targetMember = jp.mydns.kokoichi0206.model.Member(
            name = "秋元 真夏 0",
            birthday = "1993年8月20日",
            height = "154cm",
            bloodType = "B型",
            generation = "1期生",
            blogUrl = "https://blog.nogizaka46.com/manatsu.akimoto",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/akimotomanatsu.jpeg",
            group = "乃木坂",
        )

        // Act
        // Tap Twice
        composeRule.onNodeWithContentDescription("image of ${targetMember.name}").performClick()
        composeRule.onNodeWithContentDescription("image of ${targetMember.name}").performClick()

        // Assert
        verify(exactly = 2) {
            navController.navigateUp()
        }
    }

    @Test
    fun memberImage_canTapOnlyOnce() {
        // Arrange
        val targetMember = jp.mydns.kokoichi0206.model.Member(
            name = "秋元 真夏 0",
            birthday = "1993年8月20日",
            height = "154cm",
            bloodType = "B型",
            generation = "1期生",
            blogUrl = "https://blog.nogizaka46.com/manatsu.akimoto",
            imgUrl = "https://kokoichi0206.mydns.jp/imgs/nogi/akimotomanatsu.jpeg",
            group = "乃木坂",
        )

        // Act
        // Tap Twice
        composeRule.onNodeWithContentDescription("image of ${targetMember.name}").performClick()
        composeRule.onNodeWithContentDescription("image of ${targetMember.name}").performClick()
        // Navigate up (≒ press back button)
        navController.navigateUp()

        // Assert
        // Then the route is MainListView (target image is displayed)
        composeRule.onNodeWithContentDescription("image of ${targetMember.name}").assertIsDisplayed()
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
