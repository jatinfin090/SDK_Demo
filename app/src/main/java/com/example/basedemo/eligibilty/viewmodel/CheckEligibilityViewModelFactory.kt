package com.example.basedemo.eligibilty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basedemo.di.NetworkSdkModuleImpl
import com.example.basedemo.eligibilty.CheckEligibilityViewModel

class CheckEligibilityViewModelFactory(private val networkSdkModule: NetworkSdkModuleImpl
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckEligibilityViewModel::class.java)) {
            return CheckEligibilityViewModel(networkSdkModule.loanSdkLandingPageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}