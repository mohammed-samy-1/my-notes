package com.devmo.mynotes.feature_note.data.repository

import com.devmo.mynotes.feature_note.data.data_source.NoteDao
import com.devmo.mynotes.feature_note.data.data_source.toEntity
import com.devmo.mynotes.feature_note.data.data_source.toNote
import com.devmo.mynotes.feature_note.domain.model.Note
import com.devmo.mynotes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes().map {
            it.map { noteEntity ->
                noteEntity.toNote()
            }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)?.toNote()
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
    }

}