package com.vender98.bookstore.ui.books

import androidx.recyclerview.widget.DiffUtil
import ru.touchin.roboswag.recyclerview_adapters.adapters.DelegationListAdapter

class BookAdapter : DelegationListAdapter<BookItem>(CALLBACK) {

    companion object {
        private val CALLBACK = object : DiffUtil.ItemCallback<BookItem>() {

            override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean = oldItem == newItem

        }
    }

    init {
        addDelegate(BookDelegate())
    }

}
