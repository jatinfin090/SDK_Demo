package com.example.basedemo.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {


    companion object {
        @JvmStatic
        private var instance: BaseApplication? = null

        @JvmStatic
        public final fun getContext(): BaseApplication? {
            return instance
        }
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }


}