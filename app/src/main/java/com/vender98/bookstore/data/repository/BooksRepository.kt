package com.vender98.bookstore.data.repository

import com.vender98.bookstore.data.api.NetworkApi
import com.vender98.bookstore.data.persistence.dao.BooksDao
import com.vender98.bookstore.data.persistence.entities.BookEntity
import com.vender98.bookstore.dto.books.Book
import com.vender98.bookstore.dto.books.BooksWrapper
import com.vender98.bookstore.extensions.unpack
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepository @Inject constructor(
        private val api: NetworkApi,
        private val booksDao: BooksDao
) {

    fun getBooks(): Single<List<Book>> = getBooksFromCache()
            .switchIfEmpty(getBooksFromNetwork())

    fun getBooksFromNetwork(): Single<List<Book>> = api.getBooks()
            .map { it.unpack() }
            .map(BooksWrapper::books)
            .updateBooksCache()

    private fun getBooksFromCache(): Maybe<List<Book>> = booksDao.get()
            .subscribeOn(Schedulers.io())
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
            .flatMapMaybe { books ->
                if (books.isNotEmpty()) {
                    Maybe.just(books)
                } else {
                    Maybe.empty()
                }
            }


    private fun Single<List<Book>>.updateBooksCache(): Single<List<Book>> = this
            .flatMap { books ->
                Completable.fromAction {
                    booksDao.set(books.map { it.toBookEntity() })
                }
                        .andThen(Single.just(books))
            }

    private fun BookEntity.toBook() = Book(
            id = this.id,
            name = this.name,
            imageUrl = this.imageUrl
    )

    private fun Book.toBookEntity() = BookEntity(
            id = this.id,
            name = this.name,
            imageUrl = this.imageUrl
    )

}

