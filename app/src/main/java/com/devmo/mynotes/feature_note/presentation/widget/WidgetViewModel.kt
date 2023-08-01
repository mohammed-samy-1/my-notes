package com.devmo.mynotes.feature_note.presentation.widget

import androidx.annotation.WorkerThread
import com.devmo.mynotes.feature_note.domain.use_case.NoteUseCases
import javax.inject.Inject

class WidgetViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) {
    @WorkerThread
    fun getNotes() = noteUseCases.getNotes()
}