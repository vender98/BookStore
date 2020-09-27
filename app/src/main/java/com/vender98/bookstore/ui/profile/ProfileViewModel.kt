package com.vender98.bookstore.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vender98.bookstore.domain.FetchDataIfNotCachedUseCase
import com.vender98.bookstore.domain.FetchDataUseCase
import com.vender98.bookstore.domain.GetBooksUseCase
import com.vender98.bookstore.domain.GetProfileUseCase
import com.vender98.bookstore.dto.books.Book
import com.vender98.bookstore.dto.profile.Profile
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.touchin.lifecycle.event.ContentEvent
import ru.touchin.lifecycle.livedata.EmptySingleLiveEvent
import ru.touchin.lifecycle.livedata.SingleLiveEvent
import ru.touchin.lifecycle.viewmodel.RxViewModel
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
        fetchDataIfNotCachedUseCase: FetchDataIfNotCachedUseCase,
        private val fetchDataUseCase: FetchDataUseCase,
        private val getBooksUseCase: GetBooksUseCase,
        private val getProfileUseCase: GetProfileUseCase
) : RxViewModel() {

    companion object {
        private const val DATE_PATTERN = "dd MMMM yyyy"
    }

    private val _profileData = MutableLiveData<ContentEvent<ProfileData>>()
    val profileData: LiveData<ContentEvent<ProfileData>> = _profileData

    private val _error = SingleLiveEvent<Throwable>()
    val error: LiveData<Throwable> = _error

    private val _navigateToBooksScreenAction = EmptySingleLiveEvent()
    val navigateToBooksScreenAction: LiveData<Void?> = _navigateToBooksScreenAction

    init {
        fetchDataIfNotCachedUseCase.invoke()
                .andThen(getData())
                .doOnError(_error::setValue)
                .dispatchTo(_profileData)
    }

    fun fetchData() {
        val oldData = _profileData.value?.data
        fetchDataUseCase.invoke()
                .andThen(getData())
                .doOnSubscribe { _profileData.value = ContentEvent.Loading() }
                .untilDestroy(
                        onSuccess = { _profileData.value = ContentEvent.Success(it) },
                        onError = { throwable ->
                            _error.setValue(throwable)
                            _profileData.value = if (oldData != null) {
                                ContentEvent.Success(oldData)
                            } else {
                                ContentEvent.Error(throwable)
                            }
                        }
                )
    }

    fun navigateToBooksScreen() {
        _navigateToBooksScreenAction.call()
    }

    private fun getData(): Single<ProfileData> = Single.zip(
            getProfileUseCase.invoke(),
            getBooksUseCase.invoke(),
            BiFunction(this::getProfileData)
    )

    private fun getProfileData(profile: Profile, books: List<Book>): ProfileData = ProfileData(
            firstName = profile.firstName,
            lastName = profile.lastName,
            birthDate = profile.birthDate?.format(DateTimeFormatter.ofPattern(DATE_PATTERN)),
            city = profile.city,
            gender = profile.gender,
            email = profile.email,
            phoneNumber = profile.phoneNumber,
            booksCount = books.size.toString()
    )

}
