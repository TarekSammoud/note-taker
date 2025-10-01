package fr.eseo.ld.ts.notetaker.repositories


import android.app.Application
import fr.eseo.ld.ts.notetaker.data.NoteTakerDatabaseProvider
import fr.eseo.ld.ts.notetaker.model.Note


class NoteTakerRepositoryRoomImpl(
    application: Application
) : NoteTakerRepository {

    private val db = NoteTakerDatabaseProvider.getDatabase(application)
    private val noteDao = db.noteDao()

    override suspend fun getNotes(): List<Note> {
        return noteDao.getAllNotes()
    }


    override suspend fun addOrUpdateNote(note: Note) {
        noteDao.insert(note)
    }

    override suspend fun getNoteById(noteId: String): Note? {
        return noteDao.getNoteById(noteId)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }
}
