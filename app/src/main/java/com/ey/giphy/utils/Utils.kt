package com.ey.giphy.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import com.ey.giphy.GiphyApplication
object Utils {

    fun showLongToast(message: String?) {
        val toast = Toast.makeText(GiphyApplication.getContext(), message, Toast.LENGTH_LONG)
        toast.show()
    }

    fun isOnline(): Boolean {
        val connMgr = GiphyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }


}
