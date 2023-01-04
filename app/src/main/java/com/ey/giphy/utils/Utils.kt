package com.ey.giphy.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.ey.giphy.GiphyApplication
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)

    fun showLongToast(message: String?) {
        val toast = Toast.makeText(GiphyApplication.getContext(), message, Toast.LENGTH_LONG)
        toast.show()
    }

    fun isOnline(): Boolean {
        val connMgr = GiphyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS
    fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }

        // to be done :  localize
        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "just now"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
            " ${diff / MINUTE_MILLIS}  minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "an hour ago"
        } else if (diff < 24 * HOUR_MILLIS) {
            "${diff / HOUR_MILLIS} hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            "yesterday"
        } else {
            "${diff / DAY_MILLIS} days ago"
        }
    }

    fun isDifferenceLessThan10Days(date: Long): Boolean {
        val diff: Long = Calendar.getInstance().time.time - date
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return days < 10
    }
}
