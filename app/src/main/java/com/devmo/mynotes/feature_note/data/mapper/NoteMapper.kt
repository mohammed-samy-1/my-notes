package com.devmo.mynotes.feature_note.data.data_source

import com.devmo.mynotes.feature_note.data.model.NoteEntity
import com.devmo.mynotes.feature_note.domain.model.Note


fun NoteEntity.toNote(): Note {
    return Note(
        title,
        content,
        timestamp,
        color,
        id
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        title,
        content,
        timestamp,
        color,
        id
    )
}