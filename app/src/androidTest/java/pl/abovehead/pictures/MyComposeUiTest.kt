package pl.abovehead.pictures

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.abovehead.R
import pl.abovehead.cart.screens.domain.OrderItem
import pl.abovehead.pictures.domain.Picture


// file: app/src/androi
// dTest/java/com/package/PictureItemTest.kt
val title = "Apollo 11"

@RunWith(AndroidJUnit4::class)
class PictureItemTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun pictureItemTest() {
        // Create a mock picture object
        val picture = Picture(
            title = title,
            url = "https://abovehead.pl/wp-content/uploads/2023/11/Ameryka-30x40-wiz.jpg",
            shortDescription = "The first manned mission to land on the Moon",
            description = "Mission patch"
        )
//        val observerMock = mockk<(OrderItem) -> Unit>()
        val addOrder:(OrderItem) -> Unit = { _: OrderItem -> }
        // Set the content to the PictureItem composable with the mock picture and a mock addOrder function
        composeTestRule.setContent {
            AstroPhotoItem(picture = picture, addOrder = addOrder)
        }

        // Find the Text composable with the picture title and assert that it has the correct text
        composeTestRule.onNodeWithText("APOLLO 11", useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()

//        // Find the AndroidView composable with the picture description and assert that it has the correct text
//        composeTestRule.onNodeWithText(
//            "The first manned mission to land on the Moon",
//            useUnmergedTree = true
//        )
//            .assertExists()
//            .assertIsDisplayed()
//
        // Perform a click action on the Card composable and verify that the PictureDetailsActivity is launched with the correct picture extra
        composeTestRule.onNodeWithText("APOLLO 11", useUnmergedTree = true)
            .assertExists()

//        val expectedMatcher = IntentMatchers.hasExtra("item", picture)
//        intended(expectedMatcher)


        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(R.string.add_to_cart),
            useUnmergedTree = true
        ).performClick()

//         Verify that the mock addOrder function is invoked with the correct picture
//         verify { observerMock(OrderItem(title = picture.title, image = picture.url)) }
    }
}