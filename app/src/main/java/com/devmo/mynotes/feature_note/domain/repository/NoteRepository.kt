package com.devmo.mynotes.feature_note.domain.repository

import com.devmo.mynotes.feature_note.data.data_source.NoteDao
import com.devmo.mynotes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
}