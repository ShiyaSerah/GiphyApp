package com.ey.giphy.utils

import android.util.Log
import com.ey.giphy.constants.Constants.Companion.enableLog

object Logger {
    fun e(tag: String?, log: String?) {
        if (enableLog) Log.e(tag, log!!)
    }

    fun d(tag: String?, log: String?) {
        if (enableLog) Log.d(tag, log!!)
    }

    fun i(tag: String?, log: String?) {
        if (enableLog) Log.i(tag, log!!)
    }

    fun w(tag: String?, log: String?) {
        if (enableLog) Log.w(tag, log!!)
    }

    fun v(tag: String?, log: String?) {
        if (enableLog) Log.v(tag, log!!)
    }
}