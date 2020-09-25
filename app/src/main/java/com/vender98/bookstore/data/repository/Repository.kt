package com.vender98.bookstore.data.repository

import com.vender98.bookstore.data.api.NetworkApi
import com.vender98.bookstore.dto.Response
import com.vender98.bookstore.dto.ResponseStatus
import com.vender98.bookstore.dto.books.Book
import com.vender98.bookstore.dto.books.BooksWrapper
import com.vender98.bookstore.dto.profile.Profile
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val api: NetworkApi) {

    fun getProfile(): Single<Profile> = api.getProfile()
        .map { it.unpack() }

    fun getBooks(): Single<List<Book>> = api.getBooks()
        .map { it.unpack() }
        .map(BooksWrapper::books)

    private fun <T> Response<T>.unpack(): T = if (this.status == ResponseStatus.SUCCESS) {
        this.data!!
    } else {
        throw IOException(this.reason)
    }

}
