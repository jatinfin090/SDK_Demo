package com.example.basedemo.di


import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.basedemo.BuildConfig
import com.example.basedemo.datastore.DataStoreManager
import com.example.basedemo.utils.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface NetworkSDKModule {
    val loanSdkRegistrationApi: LoanSdkRegistrationApi
    val loanSdkLandingPageRepository: LoanSdkLandingPageRepository
}

class NetworkSdkModuleImpl(private val context: Context) : NetworkSDKModule {
    override val loanSdkRegistrationApi: LoanSdkRegistrationApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient(DataStoreManager(context), context))
            .build()
            .create(LoanSdkRegistrationApi::class.java)

    }

    override val loanSdkLandingPageRepository: LoanSdkLandingPageRepository by lazy {
        LoanSdkLandingPageRepository(loanSdkRegistrationApi)
    }


    private fun provideOkHttpClient(dataStoreManager: DataStoreManager, context: Context): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            val supportInterceptor = SupportInterceptor(dataStoreManager, this.context)
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            OkHttpClient.Builder()
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(180, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(supportInterceptor)
                .addInterceptor(
                    ChuckerInterceptor.Builder(context)
                        .collector(ChuckerCollector(context))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build(),
                )
                .build()
        } else {
            OkHttpClient.Builder().build()
        }
    }


    class SupportInterceptor(
        private val dataStoreManager: DataStoreManager,
        private val contextt: Context
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
            requestBuilder.addHeader(Constants.ACCEPT, Constants.APPLICATION_JSON)
            runBlocking {
                dataStoreManager.saveDeviceId("uuu")
            }
            val token = runBlocking { dataStoreManager.getAuthToken.first() }
            val timeStamp = runBlocking { dataStoreManager.getTimeStamp.first() }
            val hashDigest = runBlocking { dataStoreManager.getHashDigest.first() }
            requestBuilder.addHeader(Constants.FV_HASH_DIGEST, hashDigest)
            requestBuilder.addHeader(Constants.FV_TIMESTAMP, timeStamp)
            val deviceID = runBlocking { dataStoreManager.getDeviceId.first() }

            Log.e("k_kk", "token: $timeStamp")

            if (token.isNotEmpty()) {
                requestBuilder.addHeader(Constants.AUTHORIZATION, "${Constants.BEARER} $token")
                requestBuilder.addHeader(
                    Constants.DEVICE_SERIAL_NUMBER, deviceID
                )
            }

            val request = requestBuilder.build()
            return chain.proceed(request)
        }
    }

}