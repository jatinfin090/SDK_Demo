package com.example.basedemo.base

import android.app.Application
import com.example.basedemo.datastore.DataStoreManager
import com.example.basedemo.di.NetworkModule
import com.example.basedemo.di.NetworkModuleImpl


class BaseApplication : Application() {


    companion object {
        @JvmStatic
        private var instance: BaseApplication? = null

        @JvmStatic
        public final fun getContext(): BaseApplication? {
            return instance
        }

        lateinit var networkModule : NetworkModule
        lateinit var dataStoreManager: DataStoreManager
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        networkModule = NetworkModuleImpl(this)
        dataStoreManager = DataStoreManager(this)

    }


}