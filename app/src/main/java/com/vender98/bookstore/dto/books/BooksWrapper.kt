package com.vender98.bookstore.dto.books

import com.google.gson.annotations.Expose

data class BooksWrapper(
    @Expose
    val books: List<Book>
)
