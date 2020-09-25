package com.vender98.bookstore.data.api

import com.vender98.bookstore.BuildConfig
import com.vender98.bookstore.dto.Response
import com.vender98.bookstore.dto.books.BooksWrapper
import com.vender98.bookstore.dto.profile.Profile
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkApi {

    @GET("${BuildConfig.API_TOKEN}%2Fprofile.json?alt=media")
    fun getProfile(): Single<Response<Profile>>

    @GET("${BuildConfig.API_TOKEN}%2Fbooks.json?alt=media")
    fun getBooks(): Single<Response<BooksWrapper>>

}
