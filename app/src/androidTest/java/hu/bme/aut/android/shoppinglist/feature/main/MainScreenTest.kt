package hu.bme.aut.android.shoppinglist.feature.main

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import hu.bme.aut.android.shoppinglist.MainActivity
import org.junit.Rule
import org.junit.Test

class MainScreenTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @Test
    fun testAddProductDialogShownWhenClickedOnAddProductIcon(){
        composeTestRule.activity.setContent {
            MainScreen()
        }
        composeTestRule.onNode(hasTestTag("addProductDialog")).assertIsNotDisplayed()
        composeTestRule.onNode(hasTestTag("addProductDialogIcon")).performClick()
        composeTestRule.onNode(hasTestTag("addProductDialog")).assertIsDisplayed()
    }
}