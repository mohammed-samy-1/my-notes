package com.devmo.mynotes.feature_note.domain.use_case

import com.devmo.mynotes.feature_note.domain.model.Note
import com.devmo.mynotes.feature_note.domain.repository.NoteRepository

class UpdateNote(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note){
        repository.insertNote(note)
    }
}