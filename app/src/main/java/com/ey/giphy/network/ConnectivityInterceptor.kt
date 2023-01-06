package com.ey.giphy.network

import com.ey.giphy.GiphyApplication
import com.ey.giphy.R
import com.ey.giphy.utils.Logger
import kotlin.Throws
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException

class ConnectivityInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            val builder = chain.request().newBuilder()
            chain.proceed(builder.build())
        } catch (e: Exception) {
            throw NoConnectivityException(e)
        }
    }

    class NoConnectivityException internal constructor(private val exception: Exception) : IOException() {
        // time out exception
        override val message: String
            get() {
                Logger.e("Connectivity Interceptor", exception.message)
                return when (exception) {
                    is SocketTimeoutException ->  // time out exception
                     GiphyApplication.getContext().resources.getString(R.string.error_request_timeout)
                    is IOException ->
                        GiphyApplication.getContext().resources.getString(R.string.error_no_network)
                    else -> exception.message!!
                }
            }
    }

}