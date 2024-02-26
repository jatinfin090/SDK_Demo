package com.example.basedemo.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    open fun onBackPressed() {}
    open fun rightIconClick() {}
    open fun flatYellowButtonClick() {}
}
