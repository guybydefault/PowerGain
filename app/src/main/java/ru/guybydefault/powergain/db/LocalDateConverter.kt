package ru.guybydefault.powergain.db

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {
    @TypeConverter
    fun convertLocalDateToString(localDate: LocalDate): String {
        return localDate.toString()
    }

    @TypeConverter
    fun convertStringToLocalDate(string: String): LocalDate {
       return LocalDate.parse(string)
    }
}