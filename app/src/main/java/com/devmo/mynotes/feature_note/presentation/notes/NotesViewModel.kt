package com.devmo.mynotes.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmo.mynotes.feature_note.domain.model.Note
import com.devmo.mynotes.feature_note.domain.use_case.NoteUseCases
import com.devmo.mynotes.feature_note.domain.util.NoteOrder
import com.devmo.mynotes.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private var getNotesJob: Job? = null
    private val _state = mutableStateOf(NotesState())
    val state:State<NotesState> = _state
    private var recentlyDeletedNote: Note? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Delete -> {
                viewModelScope.launch {
                    recentlyDeletedNote = event.note
                    noteUseCases.deleteNote(event.note)
                }
            }
            is NotesEvent.Order -> {
                viewModelScope.launch {
                    if (
                        event.noteOrder::class.java == _state.value.noteOrder::class.java &&
                        _state.value.noteOrder.orderType == event.noteOrder.orderType
                    ) {
                        return@launch
                    }
                    getNotes(event.noteOrder)
                }
            }
            is NotesEvent.Restore -> {
                viewModelScope.launch {
                    recentlyDeletedNote?.let {
                        noteUseCases.insertNote(it)
                        recentlyDeletedNote = null
                    }
                }
            }
            is NotesEvent.ToggleOrderSection -> _state.value = state.value.copy(
                isOrderSectionVisible = !state.value.isOrderSectionVisible
            )
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder).onEach {
            _state.value = _state.value.copy(
                notes = it,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }
}