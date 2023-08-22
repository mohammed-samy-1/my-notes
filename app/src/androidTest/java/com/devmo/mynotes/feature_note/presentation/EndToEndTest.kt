package com.devmo.mynotes.feature_note.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.devmo.mynotes.R
import com.devmo.mynotes.core.util.TestTags
import com.devmo.mynotes.di.NoteModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NoteModule::class)
class EndToEndTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    private fun addNote(activity: MainActivity) {
        composeRule.onNodeWithContentDescription(activity.getString(R.string.add_note))
            .performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_INPUT)
            .performTextInput("title")
        composeRule.onNodeWithTag(TestTags.CONTENT_INPUT)
            .performTextInput("content")
        composeRule.onNodeWithContentDescription(activity.getString(R.string.save_note))
            .performClick()

        composeRule.onNodeWithText("title")
            .assertIsDisplayed()
        composeRule.onNodeWithText("content")
            .assertIsDisplayed()
    }

    @Test
    fun addEditNote_test() {
        val activity = composeRule.activity

        addNote(activity)
        composeRule.onNodeWithTag(TestTags.NOTE)
            .performClick()
        composeRule.onNodeWithTag(TestTags.TITLE_INPUT)
            .performTextReplacement("title2")
        composeRule.onNodeWithTag(TestTags.CONTENT_INPUT)
            .performTextReplacement("content2")
        composeRule.onNodeWithContentDescription(activity.getString(R.string.save_note))
            .performClick()

        composeRule
            .onNodeWithText("title2")
            .assertIsDisplayed()
        composeRule.onNodeWithText("content2")
            .assertIsDisplayed()

    }

    @Test
    fun saveNotes_testSorting() {
        val activity = composeRule.activity
        repeat(4) {
            composeRule.onNodeWithContentDescription(activity.getString(R.string.add_note))
                .performClick()

            composeRule.onNodeWithTag(TestTags.TITLE_INPUT)
                .performTextInput(it.toString())
            composeRule.onNodeWithTag(TestTags.CONTENT_INPUT)
                .performTextInput(it.toString())
            composeRule.onNodeWithContentDescription(activity.getString(R.string.save_note))
                .performClick()

            composeRule.onAllNodesWithText(it.toString())[0]
                .assertIsDisplayed()
        }
        composeRule.onNodeWithContentDescription(activity.getString(R.string.sort))
            .performClick()
        composeRule.onNodeWithContentDescription(activity.getString(R.string.title))
            .performClick()
        composeRule.onNodeWithContentDescription(activity.getString(R.string.descending))
            .performClick()
        composeRule.onAllNodesWithTag(TestTags.NOTE)[0]
            .assertTextEquals("3")
    }

    @Test
    fun deleteNote_undoDeleteNoteTest() {
        val activity = composeRule.activity
        addNote(activity)
        composeRule.onNodeWithContentDescription(activity.getString(R.string.delete_note))
            .performClick()
        composeRule.onNodeWithText("content")
            .assertIsNotDisplayed()
        composeRule.onNodeWithTag(TestTags.SNAKEBAR)
            .assertIsDisplayed()
        composeRule.onNodeWithText(activity.getString(R.string.undo))
            .performClick()
        composeRule.onNodeWithText("content")
            .assertIsDisplayed()
    }
}