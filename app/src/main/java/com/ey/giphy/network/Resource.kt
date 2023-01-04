package com.ey.giphy.network

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Resource<out T>(val status: Status, val data: T?, val message: Throwable?) {
    companion object {
        fun <T> success(data: T?): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = Throwable("Success"))

        fun <T> error(data: T?, message: Throwable): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}