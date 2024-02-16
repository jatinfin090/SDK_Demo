package com.example.basedemo.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.basedemo.BuildConfig
import com.example.basedemo.base.BaseApplication
import com.example.basedemo.datastore.DataStoreManager
import com.example.basedemo.utils.Constants.ACCEPT
import com.example.basedemo.utils.Constants.APPLICATION_JSON
import com.example.basedemo.utils.Constants.AUTHORIZATION
import com.example.basedemo.utils.Constants.BASE_URL
import com.example.basedemo.utils.Constants.BEARER
import com.example.basedemo.utils.Constants.CONTENT_TYPE
import com.example.basedemo.utils.Constants.DEVICE_SERIAL_NUMBER
import com.example.basedemo.utils.Constants.FV_HASH_DIGEST
import com.example.basedemo.utils.Constants.FV_TIMESTAMP
import com.example.basedemo.utils.Utility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val LOAN_SDK_REGISTRATION_RETROFIT = "loanSdkRegistrationRetrofit"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(dataStoreManager: DataStoreManager) = if ( BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        val supportInterceptor = SupportInterceptor(dataStoreManager)
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .readTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(supportInterceptor)
            .addInterceptor(
                ChuckerInterceptor.Builder(BaseApplication.getContext()!!.applicationContext)
                    .collector(ChuckerCollector(BaseApplication.getContext()!!.applicationContext))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build(),
            )
            .build()
    } else {
        OkHttpClient.Builder().build()
    }


    class SupportInterceptor(private val dataStoreManager: DataStoreManager) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader(CONTENT_TYPE, APPLICATION_JSON)
            requestBuilder.addHeader(ACCEPT, APPLICATION_JSON)
            runBlocking {
                dataStoreManager.saveDeviceId(Utility.deviceId(BaseApplication.getContext()!!.applicationContext))
            }
            val token = runBlocking { dataStoreManager.getAuthToken.first() }
            val timeStamp = runBlocking { dataStoreManager.getTimeStamp.first() }
            val hashDigest = runBlocking { dataStoreManager.getHashDigest.first() }
            requestBuilder.addHeader(FV_HASH_DIGEST, hashDigest)
            requestBuilder.addHeader(FV_TIMESTAMP, timeStamp)

            if (token.isNotEmpty()) {
                requestBuilder.addHeader(AUTHORIZATION, "$BEARER $token")
                requestBuilder.addHeader(
                    DEVICE_SERIAL_NUMBER,
                    Utility.deviceId(BaseApplication.getContext()!!.applicationContext),
                )
            }

            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    @Named(LOAN_SDK_REGISTRATION_RETROFIT)
    fun providesLoanSdkRegistrationRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesLoanSdkRegistrationApi(@Named(LOAN_SDK_REGISTRATION_RETROFIT) retrofit: Retrofit): LoanSdkRegistrationApi {
        return retrofit.create(LoanSdkRegistrationApi::class.java)
    }

    @Provides
    @Singleton
    fun dataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)

}
