package com.devmo.mynotes.feature_note.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devmo.mynotes.feature_note.presentation.add_edit_note.components.AddEditNoteScreen
import com.devmo.mynotes.feature_note.presentation.notes.components.NotesScreen
import com.devmo.mynotes.feature_note.presentation.util.Screen
import com.devmo.mynotes.feature_note.presentation.widget.component.AppWidget
import com.devmo.mynotes.ui.theme.MyNotesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var callback: Callback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNotesTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(navController = navController)
                        }
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
                    callback = Callback { id, color ->
                        navController.navigate(Screen.AddEditNotesScreen.route + "?noteId=${id}&noteColor=${color}")
                    }
                    val intent = intent
                    if (intent.action?.equals(Intent.ACTION_EDIT) == true) {
                        checkIntent(intent = intent)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkIntent(intent = intent)
    }

    private fun checkIntent(intent: Intent?) {
        if (intent != null) {
            val id = intent.getIntExtra("id", -1)
            val color = intent.getIntExtra("color", -1)
            if (id != -1 && color != -1) {
                callback.onCallback(id, color)
            }
        }
    }

    private fun interface Callback {
        fun onCallback(id: Int, color: Int)
    }

    override fun onPause() {
        super.onPause()
        val manager = GlanceAppWidgetManager(this)
//        val widget = GlanceSizeModeWidget()
        lifecycleScope.launch {
            manager.getGlanceIds(AppWidget::class.java).forEach {
                AppWidget().update(this@MainActivity, id = it)
            }
        }
    }
}

