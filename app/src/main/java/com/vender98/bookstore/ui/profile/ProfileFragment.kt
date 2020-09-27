package com.vender98.bookstore.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vender98.bookstore.BookStoreApplication
import com.vender98.bookstore.R
import com.vender98.bookstore.ui.books.BooksFragment
import com.vender98.bookstore.views.NameValueView
import ru.touchin.extensions.setOnRippleClickListener
import ru.touchin.lifecycle.event.ContentEvent
import javax.inject.Inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var contentView: View
    private lateinit var noDataView: View
    private lateinit var firstNameView: NameValueView
    private lateinit var lastNameView: NameValueView
    private lateinit var birthDateView: NameValueView
    private lateinit var cityView: NameValueView
    private lateinit var genderView: NameValueView
    private lateinit var emailView: NameValueView
    private lateinit var phoneNumberView: NameValueView
    private lateinit var booksView: NameValueView

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
        swipeRefreshLayout = rootView.findViewById(R.id.fragment_profile_swipe_refresh)
        contentView = rootView.findViewById(R.id.fragment_profile_content)
        noDataView = rootView.findViewById(R.id.fragment_profile_no_data)
        firstNameView = rootView.findViewById(R.id.fragment_profile_first_name)
        lastNameView = rootView.findViewById(R.id.fragment_profile_last_name)
        birthDateView = rootView.findViewById(R.id.fragment_profile_birth_date)
        cityView = rootView.findViewById(R.id.fragment_profile_city)
        genderView = rootView.findViewById(R.id.fragment_profile_gender)
        emailView = rootView.findViewById(R.id.fragment_profile_email)
        phoneNumberView = rootView.findViewById(R.id.fragment_profile_phone_number)
        booksView = rootView.findViewById(R.id.fragment_profile_books)
    }

    private fun configureViews() {
        swipeRefreshLayout.setOnRefreshListener { viewModel.fetchData() }
        booksView.setOnRippleClickListener { viewModel.navigateToBooksScreen() }
    }

    private fun observeViewModel() {
        viewModel.profileData.observe(viewLifecycleOwner, Observer { event ->
            swipeRefreshLayout.isRefreshing = event is ContentEvent.Loading
            if (event is ContentEvent.Success) {
                val profileData = event.data
                firstNameView.setValueOrMakeGone(profileData.firstName)
                lastNameView.setValueOrMakeGone(profileData.lastName)
                birthDateView.setValueOrMakeGone(profileData.birthDate?.let { birthDate ->
                    String.format(getString(R.string.date_template), birthDate)
                })
                cityView.setValueOrMakeGone(profileData.city)
                genderView.setValueOrMakeGone(profileData.gender)
                emailView.setValueOrMakeGone(profileData.email)
                phoneNumberView.setValueOrMakeGone(profileData.phoneNumber)
                booksView.setValueOrMakeGone(profileData.booksCount)

                noDataView.isGone = true
                contentView.isVisible = true
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { throwable ->
            Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
        })
        viewModel.navigateToBooksScreenAction.observe(viewLifecycleOwner, Observer { navigateToBooksScreen() })
    }

    private fun navigateToBooksScreen() {
        requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, BooksFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }

}
