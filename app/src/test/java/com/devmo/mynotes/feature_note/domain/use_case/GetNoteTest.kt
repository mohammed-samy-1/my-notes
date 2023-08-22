package com.devmo.mynotes.feature_note.domain.use_case

import com.devmo.mynotes.feature_note.data.TestRepo
import com.devmo.mynotes.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteTest {
    private lateinit var getNote: GetNote
    private lateinit var repository: TestRepo
    private lateinit var note: Note

    @Before
    fun setUp() {
        repository = TestRepo()
        getNote = GetNote(repository)
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
    fun `get note by id correct note`() = runBlocking {
        assertThat(
            note.id?.let {
                getNote(it)
            }?.equals(note)
        ).isTrue()
    }
}