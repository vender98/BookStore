package com.vender98.bookstore.di

import android.content.Context
import androidx.room.Room
import com.vender98.bookstore.data.persistence.Database
import com.vender98.bookstore.data.persistence.dao.ProfileDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    fun provideDatabase(context: Context): Database = Room
            .databaseBuilder(context, Database::class.java, "database")
            .build()

    @Provides
    @Singleton
    fun provideProfileDao(database: Database): ProfileDao = database.profileDao

}
