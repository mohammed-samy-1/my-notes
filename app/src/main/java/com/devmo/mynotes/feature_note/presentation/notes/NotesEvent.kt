package com.devmo.mynotes.feature_note.presentation.notes

import com.devmo.mynotes.feature_note.domain.model.Note
import com.devmo.mynotes.feature_note.domain.util.NoteOrder

sealed class NotesEvent{
    data class Order(val noteOrder: NoteOrder):NotesEvent()
    data class Delete(val note: Note):NotesEvent()
    object Restore: NotesEvent()
    object ToggleOrderSection: NotesEvent()

}
