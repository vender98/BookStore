package com.vender98.bookstore.ui.books

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vender98.bookstore.BookStoreApplication
import com.vender98.bookstore.R
import ru.touchin.lifecycle.event.ContentEvent
import javax.inject.Inject

class BooksFragment : Fragment(R.layout.fragment_books) {

    companion object {
        fun newInstance() = BooksFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<BooksViewModel> { viewModelFactory }

    lateinit var recyclerView: RecyclerView
    lateinit var noDataView: View
    lateinit var progressBarView: View

    private val recyclerAdapter = BookAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as BookStoreApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)
        configureViews()
        observeViewModel()
    }

    private fun bindViews(rootView: View) {
        recyclerView = rootView.findViewById(R.id.fragment_books_recycler)
        noDataView = rootView.findViewById(R.id.fragment_books_no_data)
        progressBarView = rootView.findViewById(R.id.fragment_books_progress_bar)
    }

    private fun configureViews() {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = recyclerAdapter
    }

    private fun observeViewModel() {
        viewModel.books.observe(viewLifecycleOwner, Observer { event ->
            if (event is ContentEvent.Success) {
                val books = event.data
                recyclerAdapter.submitList(books)

                progressBarView.isGone = true
                if (books.isNotEmpty()) {
                    recyclerView.isVisible = true
                } else {
                    noDataView.isVisible = true
                }
            }
        })
    }

}
