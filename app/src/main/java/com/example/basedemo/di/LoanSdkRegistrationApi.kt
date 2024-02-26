package com.example.basedemo.di

import com.example.basedemo.model.LoanSdkRegisterUserRequest
import com.example.basedemo.model.LoanSdkRegisterUserResponse
import com.example.basedemo.model.LoanSdkSaveRequest
import com.example.basedemo.model.UserSaveResponse
import com.example.network.BaseResponse
import com.example.network.BaseResponseArray
import retrofit2.http.Body
import retrofit2.http.POST

interface LoanSdkRegistrationApi {

    @POST(LoanSdkRegistrationUrlMapping.API_LOAN_SDK_REG_USER)
    suspend fun loanSdkRegisterUSer(
        @Body redirectRequest: LoanSdkRegisterUserRequest,
    ): BaseResponse<LoanSdkRegisterUserResponse>

    @POST(LoanSdkRegistrationUrlMapping.API_LOAN_SDK_USER_SAVE)
    suspend fun loanSdkSaveUser(
        @Body loanSdkSaveRequest: LoanSdkSaveRequest,
    ): BaseResponseArray<UserSaveResponse>

}