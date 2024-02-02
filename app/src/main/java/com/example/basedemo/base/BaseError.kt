package com.example.basedemo.base


data class BaseError(
    val `data`: Any,
    val errors: List<Error>,
    val stage: Int,
    val status: String
) {
    data class Error(
        val error_code: String,
        val error_message: String
    )
}