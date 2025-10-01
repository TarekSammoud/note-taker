package fr.eseo.ld.ts.notetaker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.eseo.ld.ts.notetaker.model.Note

@TypeConverters(NoteTakerDataConverters::class)
@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteTakerDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}