package com.example.basedemo.di

import com.example.basedemo.model.LoanSdkRegisterUserRequest
import com.example.basedemo.model.LoanSdkRegisterUserResponse
import com.example.network.BaseApiResponse2
import com.example.network.BaseResponse
import com.example.network.DataHandler2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoanSdkLandingPageRepository @Inject constructor(
    private val apiLoanSdkRegister: LoanSdkRegistrationApi,
) : BaseApiResponse2() {

    suspend fun loanSdkRegisterUSer(loanSdkRegisterUserResponse: LoanSdkRegisterUserRequest): Flow<DataHandler2<BaseResponse<LoanSdkRegisterUserResponse>>> {
        return flow {
            emit(safeApiCall { apiLoanSdkRegister.loanSdkRegisterUSer(loanSdkRegisterUserResponse) })
        }.flowOn(Dispatchers.IO)
    }


}
