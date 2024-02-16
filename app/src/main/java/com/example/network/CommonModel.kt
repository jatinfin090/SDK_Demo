package com.example.network

import com.example.basedemo.base.BaseErrorResponse

data class CommonModel(
    val errors: List<BaseErrorResponse>? = null,
    val message: String? = null,
    val requestTime: String? = null,
    val statusCode: Int? = null,
    val success: Boolean? = null,
    val stage: String? = null,
)
