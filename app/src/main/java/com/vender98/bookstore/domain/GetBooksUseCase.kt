package com.vender98.bookstore.domain

import com.vender98.bookstore.data.repository.Repository
import com.vender98.bookstore.dto.books.Book
import io.reactivex.Single
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(): Single<List<Book>> = repository.getBooks()

}
