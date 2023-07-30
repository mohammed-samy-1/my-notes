package com.devmo.mynotes.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmo.mynotes.feature_note.data.data_source.NoteDatabase
import com.devmo.mynotes.feature_note.data.repository.NoteRepositoryImpl
import com.devmo.mynotes.feature_note.domain.repository.NoteRepository
import com.devmo.mynotes.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): NoteDatabase {
        return Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            NoteDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepo(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDao = database.noteDao)
    }

    @Provides
    fun provideUseCases(noteRepository: NoteRepository):NoteUseCases{
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            insertNote = InsertNote(noteRepository),
            getNote = GetNote(noteRepository)
        )
    }
}