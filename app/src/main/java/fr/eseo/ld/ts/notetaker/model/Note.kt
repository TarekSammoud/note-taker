package fr.eseo.ld.ts.notetaker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import java.time.LocalDateTime
import java.time.ZoneId

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: String ="",
    val author : String= ""
    , val title : String = "",
    val body: String= "",
    val creationDate : Timestamp = Timestamp.now(),
    val modificationDate : Timestamp = Timestamp.now()) {

    @get:Exclude
    val creationDateLocal : LocalDateTime
        get() = creationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

    @get:Exclude
    val modificationDateLocal: LocalDateTime
        get() = modificationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

    companion object {
        fun create(
            id: String = "",
            title: String = "",
            body: String = "",
            author: String = "",
            creationDate: LocalDateTime = LocalDateTime.now(),
            modificationDate: LocalDateTime = LocalDateTime.now()
        ): Note {
            return Note(
                id = id,
                title = title,
                body = body,
                author = author,
                creationDate = com.google.firebase.Timestamp(
                    creationDate.atZone(ZoneId.systemDefault()).toInstant()
                ),
                modificationDate = com.google.firebase.Timestamp(
                    modificationDate.atZone(ZoneId.systemDefault()).toInstant()
                )
            )
        }
    }


}


