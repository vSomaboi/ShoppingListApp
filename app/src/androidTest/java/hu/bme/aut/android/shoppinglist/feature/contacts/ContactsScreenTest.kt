package hu.bme.aut.android.shoppinglist.feature.contacts

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

class ContactsScreenTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @Test
    fun testAddContactDialogShownWhenClickedOnAddContactIcon(){
        composeTestRule.activity.setContent {
            ContactsScreen()
        }
        composeTestRule.onNode(hasTestTag("contactDialog")).assertIsNotDisplayed()
        composeTestRule.onNode(hasTestTag("contactDialogButton")).performClick()
        composeTestRule.onNode(hasTestTag("contactDialog")).assertIsDisplayed()
    }

    @Test
    fun testAddContactDialogDismissedWhenCancelClicked(){
        composeTestRule.activity.setContent {
            ContactsScreen()
        }
        composeTestRule.onNode(hasTestTag("contactDialogButton")).performClick()
        composeTestRule.onNode(hasTestTag("contactDialog")).assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").performClick()
        composeTestRule.onNode(hasTestTag("contactDialog")).assertIsNotDisplayed()
    }
}