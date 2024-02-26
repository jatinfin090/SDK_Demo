package com.example.basedemo.model

import com.google.gson.annotations.SerializedName

data class LoanSdkSaveRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("pan")
    val pan: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("consentPackageIdAccepted")
    val consentPackageIdAccepted: Int
    )
