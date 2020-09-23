package com.vender98.bookstore.di

import com.vender98.bookstore.ui.profile.ProfileFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ViewModelModule::class,
            NetworkModule::class
        ]
)
interface ApplicationComponent {

    fun inject(fragment: ProfileFragment)

}
