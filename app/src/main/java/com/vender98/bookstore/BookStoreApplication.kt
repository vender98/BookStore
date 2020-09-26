package com.vender98.bookstore

import android.app.Application
import com.vender98.bookstore.di.ApplicationComponent
import com.vender98.bookstore.di.DaggerApplicationComponent
import io.reactivex.plugins.RxJavaPlugins

class BookStoreApplication : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace)
    }

}
