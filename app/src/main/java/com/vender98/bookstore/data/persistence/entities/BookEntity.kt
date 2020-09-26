package com.vender98.bookstore.data.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
        @PrimaryKey
        val id: Long,
        val name: String,
        val imageUrl: String?
)
