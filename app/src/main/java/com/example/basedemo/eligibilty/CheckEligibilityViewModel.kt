package com.example.basedemo.eligibilty

import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.basedemo.base.BaseViewModel
import com.example.basedemo.datastore.DataStoreManager
import com.example.basedemo.di.LoanSdkLandingPageRepository
import com.example.basedemo.model.LoanSdkRegisterUserRequest
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

    val usertoken = MutableLiveData<String>()

    fun getUserToken(token: String) {
        usertoken.value = token
    }

    fun loanSdkRegisterUSer(
        mobileNumber: String, model: String, manufacturer: String,
        serialNumber: String, deviceToken: String, deviceAuthType: String,
        operatingSysVersion: String, deviceType: String,
        isWhatsappActivated: Boolean, latitude: String,
        longitude: String
    ) {

        viewModelScope.launch {
        //    Rollbar.instance().info("viewModelScope.launch")
            val loanSdkRegisterUserRequest = LoanSdkRegisterUserRequest(
                mobileNumber,
                model,
                manufacturer,
                serialNumber,
                deviceToken,
                deviceAuthType,
                operatingSysVersion,
                deviceType,
                isWhatsappActivated,
                latitude,
                longitude
            )
            loanSdkLandingPageRepository.loanSdkRegisterUSer(loanSdkRegisterUserRequest)
                .collect { response ->
                    when (response) {
                        is DataHandler2.SUCCESS -> {
                            if (response.data?.success == true) {
                          //      Rollbar.instance().info("response.data?.success == true")
                                Log.e("k_response", response.data.data.toString())

                                usertoken.postValue(response.data.data.toString())
                            //    Rollbar.instance().info("after token save" )

                                //onEvent(Event.ResponseToken(response.data.data.toString()))
                            } else {
                                Log.e("k_response_error", response.data.toString())
                              //  Rollbar.instance().info("response.data?.error == true")

                            }
                        }

                        is DataHandler2.ERROR -> {
                            //Rollbar.instance().info("Error case")

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

    private fun onEvent(event: Event) {
        viewModelScope.launch { eventChannel.send(event) }
    }


    sealed class Event {
        data class ShowDialog(val message: String) : Event()
        data class HideDialog(val message: String) : Event()

        data class ResponseToken(val token: String) : Event()
    }

}