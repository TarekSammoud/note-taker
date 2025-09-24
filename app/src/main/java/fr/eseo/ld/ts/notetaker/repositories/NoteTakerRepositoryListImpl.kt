package fr.eseo.ld.ts.notetaker.repositories

import fr.eseo.ld.ts.notetaker.model.Note

class NoteTakerRepositoryListImpl : NoteTakerRepository {
    private val notes = mutableListOf<Note>()
    override suspend fun getNotes(): List<Note> {
        return notes.toList();
    }

    override suspend fun addOrUpdateNote(note: Note) {
        val existingIndex = notes.indexOfFirst{it.id == note.id}
        if(existingIndex != -1) {
            notes[existingIndex] = note
        }
        else {
            notes.add(note)
        }
    }

    override suspend fun getNoteById(noteId: String): Note? {
        return notes.find {it.id == noteId}
    }
    override suspend fun deleteNote(note: Note) {
        notes.removeIf {it.id == note.id}
    }
}