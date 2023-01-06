package com.ey.giphy.network

import com.ey.giphy.BuildConfig
import com.ey.giphy.constants.Constants
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
                addInterceptor(
                    if (Constants.APIEnvironment.valueOf(BuildConfig.ENV) != Constants.APIEnvironment.RELEASE)
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    else httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
                )
                addInterceptor(ConnectivityInterceptor())
            }.build()


            val retrofit: Retrofit = builder.client(okHttpClient).build()
            return retrofit.create(serviceClass)
        }

        fun getGiphyService(): ApiServices {
            return createService(ApiServices::class.java)
        }
    }


}




