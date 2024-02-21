package com.example.basedemo.eligibilty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basedemo.base.BaseApplication

class CheckEligibilityViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckEligibilityViewModel::class.java)) {
            return CheckEligibilityViewModel(BaseApplication.networkModule.loanSdkLandingPageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}