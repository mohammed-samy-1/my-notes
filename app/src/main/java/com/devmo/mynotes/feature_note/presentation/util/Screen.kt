package com.devmo.mynotes.feature_note.presentation.util

sealed class Screen(val route: String){
    object NotesScreen: Screen("note_screen")
    object AddEditNotesScreen: Screen("add_edit_note_screen")
}
