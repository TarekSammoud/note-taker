package fr.eseo.ld.ts.notetaker.data

import androidx.room.TypeConverter
import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class NoteTakerDataConverters {

    @TypeConverter
    fun fromTimestamp(value: Timestamp?): Long? {
        return value?.seconds
    }

    @TypeConverter
    fun toTimestamp(value: Long?): Timestamp? {
        return value?.let { Timestamp(it, 0) }
    }

    @TypeConverter
    fun fromTimeStamp(value : Long?) : LocalDateTime? {
        return value?.let{
            Instant.ofEpochSecond(value)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        }
    }
    @TypeConverter
    fun toTimeStamp(value : LocalDateTime?) : Long? {
        return value?.let{
            value
                .atZone(ZoneId.systemDefault())
                .toEpochSecond()
        }
    }

}