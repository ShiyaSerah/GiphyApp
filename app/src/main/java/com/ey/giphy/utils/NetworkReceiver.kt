package com.ey.giphy.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.ey.giphy.view.MainActivity

class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = conn.activeNetworkInfo
        (context as MainActivity).updateNetworkStatus(networkInfo != null)
    }
}