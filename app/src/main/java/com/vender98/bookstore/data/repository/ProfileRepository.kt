package com.vender98.bookstore.data.repository

import com.vender98.bookstore.data.api.NetworkApi
import com.vender98.bookstore.data.persistence.dao.ProfileDao
import com.vender98.bookstore.data.persistence.entities.ProfileEntity
import com.vender98.bookstore.dto.profile.Profile
import com.vender98.bookstore.extensions.unpack
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
        private val api: NetworkApi,
        private val profileDao: ProfileDao
) {

    fun getProfile(): Single<Profile> = getProfileFromCache()
            .switchIfEmpty(getProfileFromNetwork())

    fun getProfileFromNetwork(): Single<Profile> = api.getProfile()
            .map { it.unpack() }
            .updateProfileCache()

    private fun getProfileFromCache(): Maybe<Profile> = profileDao.get()
            .subscribeOn(Schedulers.io())
            .map { it.toProfile() }

    private fun Single<Profile>.updateProfileCache(): Single<Profile> = this
            .flatMap { profile ->
                Completable.fromAction {
                    profileDao.set(profile.toProfileEntity())
                }
                        .andThen(Single.just(profile))
            }

    private fun ProfileEntity.toProfile() = Profile(
            birthDate = this.birthDate,
            city = this.city,
            email = this.email,
            firstName = this.firstName,
            lastName = this.lastName,
            gender = this.gender,
            phoneNumber = this.phoneNumber
    )

    private fun Profile.toProfileEntity() = ProfileEntity(
            birthDate = this.birthDate,
            city = this.city,
            email = this.email,
            firstName = this.firstName,
            lastName = this.lastName,
            gender = this.gender,
            phoneNumber = this.phoneNumber
    )

}
