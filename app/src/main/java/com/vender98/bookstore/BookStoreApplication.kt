package com.vender98.bookstore

import android.app.Application
import com.vender98.bookstore.di.DaggerApplicationComponent

class BookStoreApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()

}
