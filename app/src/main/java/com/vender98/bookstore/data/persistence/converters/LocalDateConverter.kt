package com.vender98.bookstore.data.persistence.converters

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?): String? = localDate?.toString()

    @TypeConverter
    fun toLocalDate(localDateString: String?): LocalDate? = localDateString?.let(LocalDate::parse)

}
