package fr.eseo.ld.ts.notetaker.repositories

import fr.eseo.ld.ts.notetaker.model.Note

interface NoteTakerRepository {
    suspend fun getNotes() : List<Note>
    suspend fun addOrUpdateNote(note : Note)
    suspend fun getNoteById(noteId : String) : Note?
    suspend fun deleteNote(note : Note)
}