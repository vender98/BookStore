package com.vender98.bookstore.domain

import com.vender98.bookstore.data.repository.ProfileRepository
import com.vender98.bookstore.dto.profile.Profile
import io.reactivex.Single
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
        private val profileRepository: ProfileRepository
) {

    operator fun invoke(): Single<Profile> = profileRepository.getProfile().toSingle()

}
