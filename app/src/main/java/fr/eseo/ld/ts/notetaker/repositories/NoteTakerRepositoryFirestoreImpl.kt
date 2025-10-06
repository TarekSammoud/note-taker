package fr.eseo.ld.ts.notetaker.repositories

import com.google.firebase.firestore.FirebaseFirestore
import fr.eseo.ld.ts.notetaker.model.Note
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteTakerRepositoryFirestoreImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : NoteTakerRepository {

    private val notesCollection = fireStore.collection("notes")

    override suspend fun addOrUpdateNote(note: Note) {
        notesCollection.document(note.id).set(note).await()
    }

    override suspend fun deleteNote(note: Note) {
        notesCollection.document(note.id).delete().await()
    }

    override suspend fun getNotes(): List<Note> {
        return try {
            val result = notesCollection.get().await()
            result.documents.map { document ->
                document.toObject(Note::class.java) ?: throw Exception("Error converting document to Note")
            }
        } catch (e: Exception) {
            // Handle error here (log it, etc.)
            emptyList()
        }
    }

    override suspend fun getNoteById(noteId: String): Note? {
        return try {
            val document = notesCollection.document(noteId).get().await()
            document.toObject(Note::class.java)
        } catch (e: Exception) {
            null // Return null if there's an error
        }
    }

}