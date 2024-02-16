package com.example.basedemo.model


import com.google.gson.annotations.SerializedName

data class LoanSdkRegisterUserResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("otp_transaction_id")
    val otpTransactionId: String,
    @SerializedName("status")
    val status: Any,
    @SerializedName("user_id")
    val userId: String
)