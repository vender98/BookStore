package com.vender98.bookstore.dto.profile

import com.google.gson.annotations.Expose

data class Profile(
    @Expose
    val birthDate: String,
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
