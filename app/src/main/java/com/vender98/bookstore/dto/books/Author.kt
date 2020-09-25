package com.vender98.bookstore.dto.books

import com.google.gson.annotations.Expose
import java.time.LocalDate

data class Author(
    @Expose
    val id: Long,
    @Expose
    val firstName: String,
    @Expose
    val lastName: String,
    @Expose
    val birthDate: LocalDate
)
