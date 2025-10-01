package fr.eseo.ld.ts.notetaker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.eseo.ld.ts.notetaker.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    suspend fun getAllNotes() : List<Note>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId : String) : Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note : Note) : Long

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId : String)

    @Update
    suspend fun update(note : Note)
    @Delete
    suspend fun delete(note : Note)




}