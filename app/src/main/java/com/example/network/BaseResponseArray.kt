package com.example.network

import com.example.basedemo.base.BaseError
import com.google.gson.annotations.SerializedName

data class BaseResponseArray<T>(
    val statusCode: Int? = null,
    val success: Boolean? = null,
    val requestTime: String? = null,
    val message: String? = null,
    val errors: BaseError? = null,
    val data: List<T>? = null,
    @SerializedName("stage")
    val stage: Int? = null,

    val sessionId: String? = null,
)
