package com.ey.giphy

import android.app.Application
import android.content.Context
import com.ey.giphy.constants.Constants
import java.lang.ref.WeakReference

class GiphyApplication:Application() {

    companion object {
        private var weakReference: WeakReference<GiphyApplication> = WeakReference(null)

        fun getContext(): Context {
            val app = weakReference.get()
            return if (null != app) app.applicationContext else GiphyApplication().applicationContext
        }

        fun getInstance(): GiphyApplication {
            return weakReference.get()!!
        }
    }
    override fun onCreate() {
        super.onCreate()
        weakReference.clear()
        weakReference = WeakReference<GiphyApplication>(this@GiphyApplication)
        Constants.setApiEnvironment()
    }
}