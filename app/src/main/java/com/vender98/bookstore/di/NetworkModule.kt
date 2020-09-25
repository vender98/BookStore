package com.vender98.bookstore.di

import com.fatboyindustrial.gsonjavatime.Converters
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vender98.bookstore.BuildConfig
import com.vender98.bookstore.api.ProfileApi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private val CALL_ADAPTER_FACTORY = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT)
        .addCallAdapterFactory(CALL_ADAPTER_FACTORY)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .let(Converters::registerLocalDate)
        .create()

    @Singleton
    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)

}
