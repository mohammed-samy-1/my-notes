package com.devmo.mynotes.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devmo.mynotes.feature_note.data.model.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DB_NAME = "NOTES_DB"
    }
}