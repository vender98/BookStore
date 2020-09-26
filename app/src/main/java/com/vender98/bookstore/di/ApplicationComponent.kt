package com.vender98.bookstore.di

import android.content.Context
import com.vender98.bookstore.ui.books.BooksFragment
import com.vender98.bookstore.ui.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ViewModelModule::class,
            NetworkModule::class,
            PersistenceModule::class
        ]
)
interface ApplicationComponent {

    fun inject(fragment: ProfileFragment)

    fun inject(fragment: BooksFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

}
