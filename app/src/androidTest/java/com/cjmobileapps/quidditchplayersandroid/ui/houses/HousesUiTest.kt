package com.cjmobileapps.quidditchplayersandroid.ui.houses

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.cjmobileapps.quidditchplayersandroid.data.MockData
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerState
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayerState
import com.cjmobileapps.quidditchplayersandroid.testutil.assertAreDisplayed
import com.cjmobileapps.quidditchplayersandroid.testutil.launch
import com.cjmobileapps.quidditchplayersandroid.testutil.waitUntilTimeout
import com.cjmobileapps.quidditchplayersandroid.ui.QuidditchPlayersActivity
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class HousesUiTest {
    @get:Rule
    val composeRule = createEmptyComposeRule()

    @Ignore("You need a backend running to be able to test this")
    @Test
    fun housesToPlayerDetailScreenHappyFlow() =
        composeRule.launch<QuidditchPlayersActivity>(
            intentFactory = {
                Intent(it, QuidditchPlayersActivity::class.java)
            },
            onAfterLaunched = {
                // given
                // Ravenclaw player
                val player =
                    MockData
                        .ravenclawTeam()
                        .first()
                        .toPlayerEntity(MockData.mockPositions)
                        .toPlayerState()

                verifyHouseScreen()

                verifyPlayerListScreen(player)

                verifyPlayerDetailScreen(player)
            },
        )

    private fun ComposeTestRule.verifyHouseScreen() {
        // start houses screen
        waitUntilTimeout(500)

        // GRYFFINDOR
        val gryffindor = MockData.mockHouses.first { it.name == HouseName.GRYFFINDOR }
        onNodeWithContentDescription(gryffindor.name.name).assertIsDisplayed()
        onNodeWithText(gryffindor.name.name).assertIsDisplayed()
        onNodeWithText(gryffindor.emoji)

        // SLYTHERIN
        val slytherin = MockData.mockHouses.first { it.name == HouseName.SLYTHERIN }
        onNodeWithText(slytherin.name.name).assertIsDisplayed()
        onNodeWithText(slytherin.emoji).assertIsDisplayed()

        // HUFFLEPUFF
        val hufflepuff = MockData.mockHouses.first { it.name == HouseName.HUFFLEPUFF }
        onNodeWithContentDescription(hufflepuff.name.name).assertIsDisplayed()
        onNodeWithText(hufflepuff.name.name).assertIsDisplayed()
        onNodeWithText(hufflepuff.emoji).assertIsDisplayed()

        // RAVENCLAW
        val ravenclaw = MockData.mockHouses.first { it.name == HouseName.RAVENCLAW }
        onNodeWithContentDescription(ravenclaw.name.name).assertIsDisplayed()
        onNodeWithText(ravenclaw.name.name).assertIsDisplayed()
        onNodeWithText(ravenclaw.emoji).assertIsDisplayed()

        // Go to list screen
        onNodeWithContentDescription(ravenclaw.name.name).performClick()
    }

    private fun ComposeTestRule.verifyPlayerListScreen(player: PlayerState) {
        // on List screen
        waitUntilTimeout(500)

        // App bar
        onNodeWithTag("${player.house.name} title").assertIsDisplayed()

        // Image
        onNodeWithContentDescription("${player.getFullName()} Player Image").assertIsDisplayed()

        // Name and Position
        onNodeWithText(player.getFullName()).assertIsDisplayed()
        onNodeWithText(player.position).assertIsDisplayed()

        // House
        onAllNodesWithText("House").assertAreDisplayed()
        onAllNodesWithText(player.house.name).assertAreDisplayed()

        // Favorite Subject
        onAllNodesWithText("Favorite Subject").assertAreDisplayed()
        onAllNodesWithText(player.favoriteSubject)

        // Years Played
        onAllNodesWithText("Years Played").assertAreDisplayed()
        onAllNodesWithText(player.yearsPlayed.toString())

        // Status
        onAllNodesWithText("Status").assertAreDisplayed()
        onAllNodesWithText("No Status").assertAreDisplayed()

        // Go to list screen
        onNodeWithText(player.getFullName()).performClick()
    }

    private fun ComposeTestRule.verifyPlayerDetailScreen(player: PlayerState) {
        // on Detail screen
        waitUntilTimeout(500)

        // App bar
        onNodeWithTag("${player.getFullName()} title").assertIsDisplayed()

        // Image
        onNodeWithContentDescription("${player.getFullName()} Player Image").assertIsDisplayed()

        // Name and Position
        onAllNodesWithText(player.getFullName())[1].assertIsDisplayed()
        onNodeWithText(player.position).assertIsDisplayed()

        // House
        onNodeWithText("House").assertIsDisplayed()
        onNodeWithText(player.house.name).assertIsDisplayed()

        // Favorite Subject
        onNodeWithText("Favorite Subject").assertIsDisplayed()
        onNodeWithText(player.favoriteSubject).assertIsDisplayed()

        // Years Played
        onNodeWithText("Years Played").assertIsDisplayed()
        onNodeWithText(player.yearsPlayed.toString()).assertIsDisplayed()

        // Status
        onNodeWithText("Status").assertIsDisplayed()
        onNodeWithText("No Status").assertIsDisplayed()
    }
}
