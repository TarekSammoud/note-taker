package fr.eseo.ld.ts.notetaker.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.eseo.ld.ts.notetaker.model.Note
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.util.Date


@RunWith(AndroidJUnit4::class)
class NoteDaoInstrTest {
    private lateinit var database : NoteTakerDatabase
    private lateinit var noteDao : NoteDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteTakerDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        noteDao = database.noteDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertNote() = runBlocking {
        val now = LocalDateTime.now().withNano(0)

        val note = Note(
            id = "123",
            author = "tester",
            title = "Test Me",
            body = "Instrumented Testing",
            creationDate = now,
            modificationDate = now
        )
        noteDao.insert(note)
        val fetchNote = noteDao.getNoteById("123")
        assertEquals(note, fetchNote)
    }

}