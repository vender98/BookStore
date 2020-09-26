package com.vender98.bookstore.data.repository

import com.vender98.bookstore.data.api.NetworkApi
import com.vender98.bookstore.dto.books.Book
import com.vender98.bookstore.dto.books.BooksWrapper
import com.vender98.bookstore.extensions.unpack
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepository @Inject constructor(
    private val api: NetworkApi
) {

    fun getBooks(): Single<List<Book>> = api.getBooks()
        .map { it.unpack() }
        .map(BooksWrapper::books)

}

