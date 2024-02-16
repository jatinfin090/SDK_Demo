package com.example.basedemo.base

data class BaseResponses<T>(
    val statusCode: Int,
    val success: Boolean,
    val requestTime: String,
    val message: String,
    val errors: BaseErrorResponse,
    val data: List<T>
)
