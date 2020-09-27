package com.vender98.bookstore.domain

import com.vender98.bookstore.data.repository.ProfileRepository
import io.reactivex.Completable
import javax.inject.Inject

class FetchDataIfNotCachedUseCase @Inject constructor(
        private val profileRepository: ProfileRepository,
        private val fetchDataUseCase: FetchDataUseCase
) {

    operator fun invoke(): Completable = profileRepository.getProfile()
            .isEmpty
            .flatMapCompletable { isProfileNotCached ->
                if (isProfileNotCached) {
                    fetchDataUseCase.invoke()
                } else {
                    Completable.complete()
                }
            }

}
