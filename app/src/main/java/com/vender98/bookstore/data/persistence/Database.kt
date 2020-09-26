package com.vender98.bookstore.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vender98.bookstore.data.persistence.dao.BooksDao
import com.vender98.bookstore.data.persistence.dao.ProfileDao
import com.vender98.bookstore.data.persistence.entities.BookEntity
import com.vender98.bookstore.data.persistence.entities.ProfileEntity

@Database(version = 1, entities = [ProfileEntity::class, BookEntity::class])
abstract class Database : RoomDatabase() {

    abstract val profileDao: ProfileDao
    abstract val booksDao: BooksDao

}
