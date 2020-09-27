package com.vender98.bookstore.data.repository

import com.vender98.bookstore.data.api.NetworkApi
import com.vender98.bookstore.data.persistence.dao.BooksDao
import com.vender98.bookstore.data.persistence.entities.BookEntity
import com.vender98.bookstore.dto.books.Book
import com.vender98.bookstore.dto.books.BooksWrapper
import com.vender98.bookstore.extensions.unpack
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepository @Inject constructor(
        private val api: NetworkApi,
        private val booksDao: BooksDao
) {

    fun getBooks(): Single<List<Book>> = booksDao.get()
            .subscribeOn(Schedulers.io())
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }

    fun fetchBooks(): Completable = api.getBooks()
            .map { it.unpack() }
            .map(BooksWrapper::books)
            .updateCache()

    private fun Single<List<Book>>.updateCache(): Completable = this
            .flatMapCompletable { books ->
                Completable.fromAction {
                    booksDao.set(books.map { it.toBookEntity() })
                }
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

