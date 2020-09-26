package com.vender98.bookstore.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.vender98.bookstore.data.persistence.entities.BookEntity
import io.reactivex.Single

@Dao
interface BooksDao {

    @Query("SELECT * FROM book")
    fun get(): Single<List<BookEntity>>

    @Transaction
    fun set(books: List<BookEntity>) {
        clear()
        insert(books)
    }

    @Query("DELETE FROM book")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(books: List<BookEntity>)

}
