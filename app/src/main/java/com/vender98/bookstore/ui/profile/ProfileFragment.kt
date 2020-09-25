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
import com.vender98.bookstore.views.NameValueView
import ru.touchin.lifecycle.event.ContentEvent
import javax.inject.Inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }
    
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var contentView: View
    lateinit var noDataView: View
    lateinit var firstNameView: NameValueView
    lateinit var lastNameView: NameValueView
    lateinit var birthDateView: NameValueView
    lateinit var cityView: NameValueView
    lateinit var genderView: NameValueView
    lateinit var emailView: NameValueView
    lateinit var phoneNumberView: NameValueView
    lateinit var booksView: NameValueView

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
        swipeRefreshLayout.setOnRefreshListener(viewModel::fetchData)
        booksView.setOnClickListener {
            // TODO: implement this
        }
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { event ->
            swipeRefreshLayout.isRefreshing = event is ContentEvent.Loading
            when (event) {
                is ContentEvent.Success -> {
                    val profileData = event.data
                    firstNameView.setValueOrMakeGone(profileData.firstName)
                    lastNameView.setValueOrMakeGone(profileData.lastName)
                    birthDateView.setValueOrMakeGone(profileData.birthDate?.toString())
                    cityView.setValueOrMakeGone(profileData.city)
                    genderView.setValueOrMakeGone(profileData.gender)
                    emailView.setValueOrMakeGone(profileData.email)
                    phoneNumberView.setValueOrMakeGone(profileData.phoneNumber)
                    booksView.setValueOrMakeGone(profileData.booksCount)

                    noDataView.isGone = true
                    contentView.isVisible = true
                }
                is ContentEvent.Error -> {
                    val throwable = event.throwable
                    Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()

                    contentView.isGone = true
                    noDataView.isVisible = true
                }
            }
        })
    }

}
