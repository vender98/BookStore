package com.vender98.bookstore.data.persistence.dao

import androidx.room.*
import com.vender98.bookstore.data.persistence.entities.ProfileEntity
import io.reactivex.Maybe

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile LIMIT 1")
    fun get(): Maybe<ProfileEntity>

    @Transaction
    fun set(profile: ProfileEntity) {
        clear()
        insert(profile)
    }

    @Query("DELETE FROM profile")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profile: ProfileEntity)

}
