package com.ey.giphy.base_class

import android.app.Application
import androidx.lifecycle.ViewModel

abstract class BaseViewModel(application: Application):ViewModel() {


    abstract fun destroyInstances()
}