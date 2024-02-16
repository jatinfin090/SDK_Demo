package com.example.basedemo.di

import com.example.basedemo.model.LoanSdkRegisterUserRequest
import com.example.basedemo.model.LoanSdkRegisterUserResponse
import com.example.network.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoanSdkRegistrationApi {

    @POST(LoanSdkRegistrationUrlMapping.API_LOAN_SDK_REG_USER)
    suspend fun loanSdkRegisterUSer(
        @Body redirectRequest: LoanSdkRegisterUserRequest,
    ): BaseResponse<LoanSdkRegisterUserResponse>

}