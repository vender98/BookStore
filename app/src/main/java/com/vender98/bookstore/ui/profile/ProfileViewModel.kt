package com.vender98.bookstore.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vender98.bookstore.domain.GetBooksUseCase
import com.vender98.bookstore.domain.GetProfileUseCase
import com.vender98.bookstore.dto.books.Book
import com.vender98.bookstore.dto.profile.Profile
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.touchin.lifecycle.event.ContentEvent
import ru.touchin.lifecycle.viewmodel.RxViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getBooksUseCase: GetBooksUseCase
) : RxViewModel() {

    private val _viewState = MutableLiveData<ContentEvent<ProfileData>>()
    val viewState: LiveData<ContentEvent<ProfileData>> = _viewState

    init {
        fetchData(forceRefresh = false)
    }

    fun fetchData(forceRefresh: Boolean = true) {
        Single.zip(
            getProfileUseCase.invoke(forceRefresh),
            getBooksUseCase.invoke(),
            BiFunction(this::getProfileData)
        )
            .dispatchTo(_viewState)
    }

    private fun getProfileData(profile: Profile, books: List<Book>): ProfileData = ProfileData(
        firstName = profile.firstName,
        lastName = profile.lastName,
        birthDate = profile.birthDate,
        city = profile.city,
        gender = profile.gender,
        email = profile.email,
        phoneNumber = profile.phoneNumber,
        booksCount = books.size.toString()
    )

}
