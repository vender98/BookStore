package com.vender98.bookstore.dto.books

import com.google.gson.annotations.Expose

data class BooksAndAuthors(
    @Expose
    val books: List<Book>,
    @Expose
    val authors: List<Author>
)
