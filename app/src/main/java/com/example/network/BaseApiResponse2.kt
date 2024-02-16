package com.example.network

import ServerException
import com.example.basedemo.R
import com.example.basedemo.base.BaseApplication
import com.example.basedemo.utils.Constants.ERROR_TOKEN_EXPIRED
import com.example.basedemo.utils.Constants.UNAUTHORISED_USER
import retrofit2.HttpException
import java.io.IOException

/*safe api call*/
abstract class BaseApiResponse2 {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): DataHandler2<T> {

        try {
            return DataHandler2.SUCCESS(checkSuccessStatus(apiCall.invoke()))
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    return DataHandler2.NetworkError(
                        BaseApplication.getContext()?.getString(R.string.internet_issue)!!,
                    )
                }

                is HttpException -> {
                    val errorModel1: CommonModel
                    if (throwable.code() == ERROR_TOKEN_EXPIRED) {
                        errorModel1 = CommonModel(
                            errors = null,
                            message = "Token expired",
                            requestTime = null,
                            statusCode = throwable.code(),
                            success = null,
                        )
                        //  sessionExpired()
                    } else {
                        errorModel1 = CommonModel(
                            errors = null,
                            message = throwable.message.toString(),
                            requestTime = null,
                            statusCode = throwable.code(),
                            success = null,
                        )
                    }

                    return DataHandler2.ERROR(null, errorModel1)
                }

                is ServerException -> {
                    val errorModel: CommonModel
                    if (throwable.msg == UNAUTHORISED_USER) {
                        errorModel = CommonModel(
                            errors = throwable.errors,
                            message = throwable.msg,
                            requestTime = throwable.requestTime,
                            statusCode = throwable.statusCode,
                            success = throwable.success,
                        )
                        //  sessionExpired()
                    } else {

                        errorModel = CommonModel(
                            errors = throwable.errors,
                            message = throwable.msg,
                            requestTime = throwable.requestTime,
                            statusCode = throwable.statusCode,
                            success = throwable.success,
                        )
                    }
                    return DataHandler2.ERROR(null, errorModel)
                }

                else -> {
                    val errorModel = CommonModel(
                        errors = null,
                        message = throwable.message,
                        requestTime = "",
                        statusCode = 0,
                        success = false,
                    )
                    return DataHandler2.ERROR(null, errorModel)
                }
            }
        }

        /**
         * This func determines if whenever any response code is 401 we need to clear data and logout user
         */
        /* suspend fun sessionExpired() {
             val context = BaseApplication.getContext()?.applicationContext
             context?.let {
                 val intent = Intent(it, SessionExpiredActivity::class.java)
                 intent.flags =
                     Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                 it.startActivity(intent)
             }
         }*/
    }

    /* private suspend fun sessionExpired() {
         val context = BaseApplication.getContext()?.applicationContext
         context?.let {
             val intent = Intent(it, SessionExpiredActivity::class.java)
             intent.flags =
                 Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
             it.startActivity(intent)
         }
     }*/

    private fun <T> checkSuccessStatus(value: T): T {
        if (value is BaseResponse<*>) {
            if (value.success == true) {
                return value
            } else {
                throw ServerException(
                    msg = value.message,
                    requestTime = value.requestTime,
                    statusCode = value.statusCode,
                    success = value.success,
                )
            }
        }
        return value!!
    }
}
