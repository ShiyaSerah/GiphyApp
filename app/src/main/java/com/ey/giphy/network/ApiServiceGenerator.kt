package com.ey.giphy.network

import com.ey.giphy.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class ApiServiceGenerator {
    companion object {
        private val httpLoggingInterceptor = HttpLoggingInterceptor()
        private const val BASE_URL = BuildConfig.BASE_URL

        private fun <S> createService(serviceClass: Class<S>): S {
            val builder = Retrofit.Builder().apply {
                baseUrl(BASE_URL)
                addConverterFactory(JacksonConverterFactory.create())
            }

            val okHttpClient = OkHttpClient.Builder().apply {
                readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                addInterceptor(ConnectivityInterceptor())
            }.build()


            val retrofit: Retrofit = builder.client(okHttpClient).build()
            return retrofit.create(serviceClass)
        }
        fun getNewsService(): ApiServices {
            return createService(ApiServices::class.java)
        }
    }


}




