package com.vender98.bookstore.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vender98.bookstore.R
import ru.touchin.roboswag.recyclerview_adapters.adapters.ItemAdapterDelegate

class BookDelegate : ItemAdapterDelegate<BookViewHolder, BookItem>() {

    override fun onCreateViewHolder(parent: ViewGroup): BookViewHolder = BookViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
    )

    override fun onBindViewHolder(
        holder: BookViewHolder,
        item: BookItem,
        adapterPosition: Int,
        collectionPosition: Int,
        payloads: MutableList<Any>
    ) = holder.bind(item)

}
