package com.vender98.bookstore.dto.books

import com.google.gson.annotations.Expose

data class Book(
    @Expose
    val id: Long,
    @Expose
    val name: String,
    @Expose
    val authors: List<Long>,
    @Expose
    val imageUrl: String?
)
