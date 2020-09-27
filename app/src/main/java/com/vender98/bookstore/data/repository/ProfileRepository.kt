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

    fun getProfile(): Maybe<Profile> = profileDao.get()
            .subscribeOn(Schedulers.io())
            .map { it.toProfile() }

    fun fetchProfile(): Completable = api.getProfile()
            .map { it.unpack() }
            .updateCache()

    private fun Single<Profile>.updateCache(): Completable = this
            .flatMapCompletable { profile ->
                Completable.fromAction {
                    profileDao.set(profile.toProfileEntity())
                }
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
