package com.devmo.mynotes.feature_note.domain.use_case

import com.devmo.mynotes.feature_note.data.TestRepo
import com.devmo.mynotes.feature_note.domain.model.Note
import com.devmo.mynotes.feature_note.domain.util.NoteOrder
import com.devmo.mynotes.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {
    private lateinit var getNotes: GetNotes
    private lateinit var repository: TestRepo

    @Before
    fun setUp() {
        repository = TestRepo()
        getNotes = GetNotes(repository)
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
        runBlocking {
            notes.forEach {
                repository.insertNote(it)
            }
        }
    }

    @Test
    fun `get notes order by title ascending correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
        for (i in 0..notes.size -2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `get notes order by title descending correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()
        for (i in 0..notes.size -2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `get notes order by date ascending correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()
        for (i in 0..notes.size -2){
            assertThat(notes[i].timestamp).isLessThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `get notes order by date descending correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()
        for (i in 0..notes.size -2){
            assertThat(notes[i].timestamp).isGreaterThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `get notes order by color ascending correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()
        for (i in 0..notes.size -2){
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }
    }

    @Test
    fun `get notes order by color descending correct order`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()
        for (i in 0..notes.size -2){
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }
    }
}