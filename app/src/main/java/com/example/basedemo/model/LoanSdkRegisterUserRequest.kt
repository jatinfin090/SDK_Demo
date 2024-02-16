package com.example.basedemo.model

import com.google.gson.annotations.SerializedName

data class LoanSdkRegisterUserRequest(
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("manufacturer")
    val manufacturer: String,
    @SerializedName("serial_number")
    val serialNumber: String,
    @SerializedName("device_token")
    val deviceToken: String,
    @SerializedName("device_auth_type")
    val deviceAuthType: String,
    @SerializedName("operating_sys_version")
    val operatingSysVersion: String,
    @SerializedName("device_type")
    val deviceType: String,
    @SerializedName("is_whatsapp_activated")
    val isWhatsappActivated: Boolean,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String
    )
