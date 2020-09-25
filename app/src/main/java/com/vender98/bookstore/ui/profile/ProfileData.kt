package com.vender98.bookstore.ui.profile

import java.time.LocalDate

data class ProfileData(
    val firstName: String?,
    val lastName: String?,
    val birthDate: LocalDate?,
    val city: String?,
    val gender: String?,
    val email: String,
    val phoneNumber: String?,
    val booksCount: String
)
