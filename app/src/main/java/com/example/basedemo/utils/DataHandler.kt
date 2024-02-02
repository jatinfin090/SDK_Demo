package com.example.basedemo.utils

import com.example.basedemo.base.BaseError

sealed class DataHandler<T>(
    val data: T? = null,
    val message: String? = null,
    val errors: BaseError? = null,
    val stage: Int? = null,
) {
    class SUCCESS<T>(data: T) : DataHandler<T>(data)
    class ERROR<T>(message: String, data: T? = null, baseError: BaseError? = null) :
        DataHandler<T>(data, message, baseError)
    class LOADING<T> : DataHandler<T>()
    class NetworkError<T>(val msg: String) : DataHandler<T>()
}
