package com.example.basedemo.eligibilty

import androidx.lifecycle.ViewModelProvider

fun <VM : CheckEligibilityViewModel> viewModelFactory(initializer: () -> VM): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        fun <T : CheckEligibilityViewModel> create(modelClass: Class<T>): T {
            return initializer() as T
        }
    }
}