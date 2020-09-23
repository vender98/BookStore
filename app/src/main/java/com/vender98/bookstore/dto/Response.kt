package com.vender98.bookstore.dto

import com.google.gson.annotations.Expose

data class Response<T>(
    @Expose
    val data: T?,
    @Expose
    val reason: String?,
    @Expose
    val status: ResponseStatus
)
