package com.vender98.bookstore.ui.books

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vender98.bookstore.R
import ru.touchin.extensions.findViewById

class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val nameView: TextView = findViewById(R.id.item_book_name)
    private val imageView: ImageView = findViewById(R.id.item_book_image)

    fun bind(item: BookItem) {
        nameView.text = item.name
        Glide.with(itemView)
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_book_placeholder)
            .error(R.drawable.ic_book_placeholder)
            .into(imageView)
    }

}
