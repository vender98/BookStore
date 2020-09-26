package com.vender98.bookstore.data.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vender98.bookstore.data.persistence.converters.LocalDateConverter
import java.time.LocalDate

@Entity(tableName = "profile")
@TypeConverters(LocalDateConverter::class)
data class ProfileEntity(
        @PrimaryKey
        val email: String,
        val birthDate: LocalDate?,
        val city: String?,
        val firstName: String?,
        val lastName: String?,
        val gender: String?,
        val phoneNumber: String?
)
