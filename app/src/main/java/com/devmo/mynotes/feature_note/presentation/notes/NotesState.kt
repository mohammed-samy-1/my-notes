package com.devmo.mynotes.feature_note.presentation.notes

import com.devmo.mynotes.feature_note.domain.model.Note
import com.devmo.mynotes.feature_note.domain.util.NoteOrder
import com.devmo.mynotes.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = listOf(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
