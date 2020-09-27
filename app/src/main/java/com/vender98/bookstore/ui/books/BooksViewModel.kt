package com.vender98.bookstore.ui.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vender98.bookstore.domain.GetBooksUseCase
import com.vender98.bookstore.dto.books.Book
import ru.touchin.lifecycle.event.ContentEvent
import ru.touchin.lifecycle.viewmodel.RxViewModel
import javax.inject.Inject

class BooksViewModel @Inject constructor(
    getBooksUseCase: GetBooksUseCase
) : RxViewModel() {

    private val _books = MutableLiveData<ContentEvent<List<BookItem>>>()
    val books: LiveData<ContentEvent<List<BookItem>>> = _books

    init {
        getBooksUseCase.invoke()
                .map { books ->
                    books.map { it.toBookItem() }
                }
                .dispatchTo(_books)
    }

    private fun Book.toBookItem() = BookItem(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl
    )

}
