package fr.eseo.ld.ts.notetaker.model

import java.time.LocalDateTime

class Note(val id: String, val author : String
, val title : String,
    val body: String,
    val creationDate: LocalDateTime,
    val modificationDate: LocalDateTime) {

}