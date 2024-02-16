package com.example.network

sealed class DataHandler2<T>(val data: T? = null, val common: CommonModel? = null) {
    class SUCCESS<T>(data: T, common: CommonModel? = null) : DataHandler2<T>(data, common)
    class ERROR<T>(data: T? = null, errors: CommonModel? = null) :
        DataHandler2<T>(data, errors)

    class LOADING<T> : DataHandler2<T>()
    class NetworkError<T>(val msg: String) : DataHandler2<T>()
}
