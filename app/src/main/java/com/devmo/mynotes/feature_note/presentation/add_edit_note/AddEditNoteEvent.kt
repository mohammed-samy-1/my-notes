package com.devmo.mynotes.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{
    data class TitleChanged(val value: String): AddEditNoteEvent()
    data class TitleFocusChanged(val focusState: FocusState): AddEditNoteEvent()
    data class ContentChanged(val value: String): AddEditNoteEvent()
    data class ContentFocusChanged(val focusState: FocusState): AddEditNoteEvent()
    data class ColorChanged(val color: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}