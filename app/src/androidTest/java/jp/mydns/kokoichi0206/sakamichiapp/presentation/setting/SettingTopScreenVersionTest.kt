package jp.mydns.kokoichi0206.sakamichiapp.presentation.setting

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
import jp.mydns.kokoichi0206.sakamichiapp.presentation.setting.components.VersionInfo
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.TestTags
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class SettingTopScreenVersionTest {

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
            VersionInfo {
            }
        }
    }

    @Test
    fun settingVersion_display() {
        // Arrange

        // Act

        // Assert
        composeRule
            .onNodeWithTag(TestTags.SETTING_VERSION)
            .assertExists()
    }

    @Test
    fun onVersionClickedOneTime_doesNotDisplaySnackBar() {
        // Arrange
        composeRule
            .onNodeWithTag(TestTags.SNACK_BAR_TEXT)
            .assertDoesNotExist()

        // Act
        composeRule
            .onNodeWithTag(TestTags.SETTING_VERSION)
            .performClick()

        // Assert
        composeRule
            .onNodeWithTag(TestTags.SNACK_BAR_TEXT)
            .assertDoesNotExist()
    }

    @Test
    fun onVersionClickedSeveralTimes_displaySnackBar() {
        // Arrange
        composeRule
            .onNodeWithTag(TestTags.SNACK_BAR_TEXT)
            .assertDoesNotExist()

        // Act
        composeRule
            .onNodeWithTag(TestTags.SETTING_VERSION)
            .performClick()
            .performClick()
            .performClick()
            .performClick()
            .performClick()

        // Assert
        composeRule
            .onNodeWithTag(TestTags.SNACK_BAR_TEXT)
            .assertExists()
    }

    @Test
    fun onVersionClickedSevenTimes_displaySpecialSnackBar() {
        // Arrange
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedStr = context.resources.getString(R.string.developer_success_snack_bar_text)
        composeRule
            .onNodeWithTag(TestTags.SNACK_BAR_TEXT)
            .assertDoesNotExist()

        // Act
        composeRule
            .onNodeWithTag(TestTags.SETTING_VERSION)
            .performClick()
            .performClick()
            .performClick()
            .performClick()
            .performClick()
            .performClick()
            .performClick()

        // Assert
        composeRule
            .onNodeWithTag(TestTags.SNACK_BAR_TEXT)
            .assertExists()
            .assertTextEquals(expectedStr)
    }
}
