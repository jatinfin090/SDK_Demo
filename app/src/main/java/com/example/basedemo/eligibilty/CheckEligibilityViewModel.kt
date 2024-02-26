package com.example.basedemo.eligibilty

import android.os.Build
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.basedemo.base.BaseViewModel
import com.example.basedemo.datastore.DataStoreManager
import com.example.basedemo.di.LoanSdkLandingPageRepository
import com.example.basedemo.model.LoanSdkRegisterUserRequest
import com.example.basedemo.model.LoanSdkSaveRequest
import com.example.basedemo.utils.Constants
import com.example.basedemo.utils.Utility
import com.example.network.DataHandler2
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class CheckEligibilityViewModel(private val loanSdkLandingPageRepository: LoanSdkLandingPageRepository) :
    BaseViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventFlow = eventChannel.receiveAsFlow()

    fun loanSdkRegisterUSer(
        loanSdkRegisterUserRequest: LoanSdkRegisterUserRequest,
        dataStoreManager: DataStoreManager
    ) {
        viewModelScope.launch {
            loanSdkLandingPageRepository.loanSdkRegisterUSer(loanSdkRegisterUserRequest)
                .collect { response ->
                    when (response) {
                        is DataHandler2.SUCCESS -> {
                            if (response.data?.success == true) {
                                dataStoreManager.saveAuthToken(response.data.data?.accessToken.toString())
                                onEvent(Event.ResponseToken(response.data.data?.accessToken.toString()))
                            } else {
                                Log.e("response_error", response.data.toString())
                            }
                        }

                        is DataHandler2.ERROR -> {
                        }

                        else -> {}
                    }
                }
        }
    }


    fun loanSdkSaveUser(loanSdkSaveRequest: LoanSdkSaveRequest) {
        viewModelScope.launch {
            loanSdkLandingPageRepository.loanSdkSaveUser(loanSdkSaveRequest)
                .collect { response ->
                    when (response) {
                        is DataHandler2.SUCCESS -> {
                            onEvent(Event.ResponseSave(response.data?.message.toString()))
                        }

                        is DataHandler2.ERROR -> {
                            Log.e("k_Saveresponseerror", response.common?.message.toString())
                        }

                        else -> {}
                    }
                }
        }
    }


    fun getTimeStampDigest(dataStoreManager: DataStoreManager) {
        viewModelScope.launch {
            val timeStamp = (System.currentTimeMillis() / 1000).toString()
            val signature = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Utility.generateHmacSha256Signature(Constants.SECRET_KEY, timeStamp)
            } else {
                TODO("VERSION.SDK_INT < O")
            }

            dataStoreManager.saveHashDigest(signature)
            dataStoreManager.saveTimeStamp(timeStamp)
        }
    }

    fun collectDOB() {
        onEvent(Event.DOB("dob"))

    }

    private fun onEvent(event: Event) {
        viewModelScope.launch { eventChannel.send(event) }
    }


    sealed class Event {
        data class ShowDialog(val message: String) : Event()
        data class HideDialog(val message: String) : Event()

        data class ResponseToken(val token: String) : Event()
        data class ResponseSave(val response: String) : Event()

        data class DOB(val dob: String) : Event()
    }

}