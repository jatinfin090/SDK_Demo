package com.example.basedemo.base

import android.app.Application
import com.example.basedemo.datastore.DataStoreManager
import com.example.basedemo.di.NetworkSDKModule
import com.example.basedemo.di.NetworkSdkModuleImpl


class BaseSDKApplication : Application() {


    companion object {
        @JvmStatic
        private var instance: BaseSDKApplication? = null

        @JvmStatic
        public final fun getContext(): BaseSDKApplication? {
            return instance
        }

         var networkSdkModule : NetworkSDKModule
             get() {
                 TODO()
             }
             set(value) {

             }
        lateinit var dataStoreManager: DataStoreManager
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        networkSdkModule = NetworkSdkModuleImpl(applicationContext)
        dataStoreManager = DataStoreManager(applicationContext)

    }


}