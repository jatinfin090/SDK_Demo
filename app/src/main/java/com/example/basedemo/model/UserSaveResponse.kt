package com.example.basedemo.model
import com.google.gson.annotations.SerializedName

data class UserSaveResponse(
    @SerializedName("data")
    val `data`: List<Any>,
    @SerializedName("errors")
    val errors: Any,
    @SerializedName("message")
    val message: String,
    @SerializedName("request_time")
    val requestTime: String,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("success")
    val success: Boolean
)