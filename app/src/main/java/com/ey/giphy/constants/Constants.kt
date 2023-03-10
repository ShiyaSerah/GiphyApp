package com.ey.giphy.constants

import com.ey.giphy.BuildConfig


class Constants {

    companion object{

        const val API_KEY = "79JpxpypaQzogciYQs3VI9YNysNxGgJN"

        @JvmField
        var enableLog = true

        fun setApiEnvironment(){
            enableLog = when(APIEnvironment.valueOf(BuildConfig.ENV)){
                APIEnvironment.RELEASE ->{
                    false
                }
                APIEnvironment.DEBUG ->{
                    true
                }
            }
        }
    }

    enum class APIEnvironment {
        RELEASE, DEBUG
    }
}