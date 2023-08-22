package com.devmo.mynotes.feature_note.domain.use_case

import com.devmo.mynotes.feature_note.data.TestRepo
import com.devmo.mynotes.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteNoteTest {
    private lateinit var deleteNote: DeleteNote
    private lateinit var repository: TestRepo
    private lateinit var note: Note

    @Before
    fun setUp() {
        repository = TestRepo()
        deleteNote = DeleteNote(repository)
        val notes = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notes.add(
                Note(
                    c.toString(),
                    c.toString(),
                    index.toLong(),
                    color = index,
                    id = index
                )
            )
        }
        notes.shuffle()
        note = notes.random()
        runBlocking {
            notes.forEach {
                repository.insertNote(it)
            }
        }
    }
    @Test
    fun `delete note works correctly`() = runBlocking{
        deleteNote(note)
        assertThat(
            repository.getNotes().first().contains(note)
        ).isFalse()
    }
}

