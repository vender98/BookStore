package com.vender98.bookstore.domain

import com.vender98.bookstore.data.repository.BooksRepository
import com.vender98.bookstore.dto.books.Book
import io.reactivex.Single
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val booksRepository: BooksRepository
) {

    operator fun invoke(forceRefresh: Boolean): Single<List<Book>> = if(forceRefresh) {
        booksRepository.getBooksFromNetwork()
    } else {
        booksRepository.getBooks()
    }

}
