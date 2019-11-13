package com.rickmortyapp.domain.remote

import com.google.gson.GsonBuilder
import com.rickmortyapp.domain.BuildConfig
import com.rickmortyapp.domain.datasource.CharacterDatasource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

val characterRetrofit by lazy { instantiate<CharacterDatasource>(BuildConfig.API_URL) }

private inline fun <reified T> instantiate(url: String = BuildConfig.API_URL): T =
        Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(createGsonConverter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
                .create(T::class.java)

private val okHttpClient: OkHttpClient by lazy {
    val httpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    OkHttpClient.Builder()
            .connectTimeout(180L, TimeUnit.SECONDS)
            .readTimeout(180L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(createNoContentInterceptor())
            .build()
}

private fun createGsonConverter(): Converter.Factory {
    val gsonBuilder = GsonBuilder()
    val gson = gsonBuilder.create()

    return GsonConverterFactory.create(gson)
}


private fun createNoContentInterceptor(): Interceptor {
    return object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
            val response = chain.proceed(requestBuilder.build())

            if (response.code == 204) {
                throw NoContentException("There is no content")
            }

            return response
        }
    }
}

private class NoContentException(override val message: String?) : Throwable(message)