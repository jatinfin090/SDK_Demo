package com.example.network

import com.example.basedemo.base.BaseError
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    val statusCode: Int? = null,
    val success: Boolean? = null,
    val requestTime: String? = null,
    val message: String? = null,
    val errors: BaseError? = null,
    val data: T? = null,
    @SerializedName("stage")
    val stage: Int? = null,

    val sessionId: String? = null,
)
