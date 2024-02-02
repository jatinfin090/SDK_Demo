/*
package com.example.basedemo.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.basedemo.BuildConfig
import com.example.basedemo.base.BaseApplication
import com.finvasia.jumpp.datastore.DataStoreManager
import com.finvasia.jumpp.utils.Constants.ACCEPT
import com.finvasia.jumpp.utils.Constants.APPLICATION_JSON
import com.finvasia.jumpp.utils.Constants.AUTHORIZATION
import com.finvasia.jumpp.utils.Constants.BEARER
import com.finvasia.jumpp.utils.Constants.CONTENT_TYPE
import com.finvasia.jumpp.utils.Constants.DEVICE_SERIAL_NUMBER
import com.finvasia.jumpp.utils.Constants.FV_HASH_DIGEST
import com.finvasia.jumpp.utils.Constants.FV_TIMESTAMP
import com.finvasia.jumpp.utils.Utility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val STOCK_RETROFIT = "stockRetrofit"
const val STOCK_MUTUAL_FUND_RETROFIT = "stockMutualFundRetrofit"
const val BBPS_RETROFIT = "bbpsRetrofit"
const val ACCOUNT_AGGREGATOR_RETROFIT = "accountAggregatorRetrofit"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(dataStoreManager: DataStoreManager) = if (BuildConfig.DEBUG) {
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



 */
/*   @Provides
    @Singleton
    @Named(STOCK_MUTUAL_FUND_RETROFIT)
    fun providesMutualFundsRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL + APP_KEY_MF)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }*//*




  */
/*  @Provides
    fun providesMutualFundApi(@Named(STOCK_MUTUAL_FUND_RETROFIT) retrofit: Retrofit): ApiMutualFundService {
        return retrofit.create(ApiMutualFundService::class.java)
    }*//*



    class SupportInterceptor(val dataStoreManager: DataStoreManager) : Interceptor {
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

*/
/*    @Provides
    @Singleton
    fun dataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)*//*

}
*/
