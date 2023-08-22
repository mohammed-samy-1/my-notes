package com.devmo.mynotes.feature_note.presentation.add_edit_note.components

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devmo.mynotes.R
import com.devmo.mynotes.core.util.TestTags
import com.devmo.mynotes.core.util.TestTags.Companion.SNAKEBAR
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
class AddEditNoteScreenTest{
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
                    startDestination = Screen.AddEditNotesScreen.route
                ) {
                    composable(
                        route = Screen.AddEditNotesScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(navController = navController, color)
                    }
                }
            }
        }
    }

    @Test
    fun addEmptyTitleOrContent_showsSnakeBar(){
        val activity = composeRule.activity
        composeRule.onNodeWithContentDescription(activity.getString(R.string.save_note))
            .performClick()
        composeRule.onNodeWithTag(SNAKEBAR)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.TITLE_INPUT)
            .performTextInput("title")
        composeRule.onNodeWithTag(SNAKEBAR)
            .assertIsDisplayed()

    }
}