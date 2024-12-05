package hu.bme.aut.android.shoppinglist.feature.createshoppinglist

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import hu.bme.aut.android.shoppinglist.MainActivity
import org.junit.Rule
import org.junit.Test

class CreateShoppingListScreenTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @Test
    fun testAddProductDialogShownWhenClickedOnAddProductIcon(){
        composeTestRule.activity.setContent {
            CreateShoppingListScreen()
        }
        composeTestRule.onNode(hasTestTag("productSelectionDialog")).assertIsNotDisplayed()
        composeTestRule.onNode(hasTestTag("productSelectionDialogIcon")).performClick()
        composeTestRule.onNode(hasTestTag("productSelectionDialog")).assertIsDisplayed()
    }

    @Test
    fun testDialogDismissedWhenCancelClicked(){
        composeTestRule.activity.setContent {
            CreateShoppingListScreen()
        }
        composeTestRule.onNode(hasTestTag("productSelectionDialogIcon")).performClick()
        composeTestRule.onNode(hasTestTag("productSelectionDialog")).assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").performClick()
        composeTestRule.onNode(hasTestTag("productSelectionDialog")).assertIsNotDisplayed()
    }
}