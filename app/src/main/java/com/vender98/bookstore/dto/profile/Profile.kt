package com.vender98.bookstore.dto.profile

import com.google.gson.annotations.Expose
import java.time.LocalDate

data class Profile(
    @Expose
    val birthDate: LocalDate?,
    @Expose
    val city: String?,
    @Expose
    val email: String,
    @Expose
    val firstName: String?,
    @Expose
    val lastName: String?,
    @Expose
    val gender: String?,
    @Expose
    val phoneNumber: String?
)
