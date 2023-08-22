package com.devmo.mynotes.feature_note.domain.use_case

import com.devmo.mynotes.feature_note.data.TestRepo
import com.devmo.mynotes.feature_note.domain.model.InvalidNoteException
import com.devmo.mynotes.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertNoteTest {
    private lateinit var repository: TestRepo
    private lateinit var insertNote: InsertNote

    @Before
    fun setUp() {
        repository = TestRepo()
        insertNote = InsertNote(repository)
    }

    @Test
    fun `insert note empty title check`() = runBlocking {
        try {
            insertNote(
                Note(
                    "",
                    "content",
                    1,
                    1,
                    1
                )
            )
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(InvalidNoteException::class.java)
            assertThat(e.localizedMessage?.equals("Note title can't be empty.")).isTrue()
        }
    }

    @Test
    fun `insert note empty content check`() = runBlocking {
        try {
            insertNote(
                Note(
                    "title",
                    "",
                    1,
                    1,
                    1
                )
            )
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(InvalidNoteException::class.java)
            assertThat(e.localizedMessage?.equals("Note content can't be empty.")).isTrue()
        }
    }

    @Test
    fun `insert mote correctly test`() = runBlocking {
        val note = Note(
            "title",
            "content",
            1,
            1,
            1
        )
        insertNote(
            note
        )

        runBlocking {
            assertThat(
                repository.getNotes().first()
            ).contains(note)
        }
    }


}