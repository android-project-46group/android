package jp.mydns.kokoichi0206.sakamichiapp.presentation.member_list

import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import jp.mydns.kokoichi0206.sakamichiapp.di.AppModule
import jp.mydns.kokoichi0206.sakamichiapp.domain.model.Member
import jp.mydns.kokoichi0206.sakamichiapp.presentation.MainActivity
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Constants
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Screen
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.getJsonFromMember
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
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
            MemberListScreen(
                navController = navController,
            )
        }
    }

    @Test
    fun memberImage_display() {
        // Members are defined in data/remote/MockSakamichiApi
        composeRule.onNodeWithContentDescription("image of 秋元 真夏").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("image of 生田 絵梨花").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("image of 伊藤 理々杏").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("image of 岩本 蓮加").assertIsDisplayed()
    }

    @Test
    fun memberImage_canTap() {
        // Members are defined in data/remote/MockSakamichiApi
        composeRule.onNodeWithContentDescription("image of 秋元 真夏").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("image of 秋元 真夏").assertHasClickAction()
    }

    @Test
    fun memberImage_canTapCorrectly() {
        // Arrange
        val targetMember = Member(
            name = "秋元 真夏",
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
            navController.navigate(
                Screen.MemberDetailScreen.route
                        + "/${Constants.NAV_PARAM_MEMBER_PROPS}=${getJsonFromMember(targetMember)}"
            )
        }
    }

    @Test
    fun memberImage_canTapOnlyOnce() {
        // Arrange
        val targetMember = Member(
            name = "秋元 真夏",
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
        composeRule.onNodeWithContentDescription("image of 秋元 真夏").assertIsDisplayed()
    }
}
