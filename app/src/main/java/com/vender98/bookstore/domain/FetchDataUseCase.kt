package com.vender98.bookstore.domain

import com.vender98.bookstore.data.repository.BooksRepository
import com.vender98.bookstore.data.repository.ProfileRepository
import io.reactivex.Completable
import javax.inject.Inject

class FetchDataUseCase @Inject constructor(
        private val profileRepository: ProfileRepository,
        private val booksRepository: BooksRepository
) {

    operator fun invoke(): Completable = Completable.concatArray(
            profileRepository.fetchProfile(),
            booksRepository.fetchBooks()
    )

}
