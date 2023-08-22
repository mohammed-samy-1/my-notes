package com.devmo.mynotes.feature_note.presentation.notes.components

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devmo.mynotes.R
import com.devmo.mynotes.core.util.TestTags
import com.devmo.mynotes.di.NoteModule
import com.devmo.mynotes.feature_note.presentation.MainActivity
import com.devmo.mynotes.feature_note.presentation.util.Screen
import com.devmo.mynotes.ui.theme.MyNotesTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NoteModule::class)
class NotesScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()
    @Before
    fun setup() {
        hiltRule.inject()

        composeRule.activity.setContent {
            val navController = rememberNavController()
            MyNotesTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible(){
        val activity = composeRule.activity
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(activity.getString(R.string.sort))
            .performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(activity.getString(R.string.sort))
            .performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
    }

}