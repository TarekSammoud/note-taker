package fr.eseo.ld.ts.notetaker.data

import android.content.Context
import androidx.room.Room.databaseBuilder

object NoteTakerDatabaseProvider {
    @Volatile
    private var INSTANCE : NoteTakerDatabase? = null


    fun getDatabase(context : Context) : NoteTakerDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = databaseBuilder(
                context.applicationContext,
                NoteTakerDatabase::class.java,
                "note-database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}