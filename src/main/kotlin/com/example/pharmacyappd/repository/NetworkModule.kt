package com.example.pharmacyappd.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NetworkModule private constructor() {
    private val retrofit: Retrofit
    val pharmacyApi: PharmacyApi

    init {
        retrofit = retrofitInstance(httpClient())
        pharmacyApi = getApiService(retrofit)
    }

    companion object {
        private const val BASE_URL = "http://5.56.132.82:8080/"
        private var instanceValue: NetworkModule? = null

        val instance: NetworkModule
            get() {
                if (instanceValue == null) {
                    instanceValue = NetworkModule()
                }
                return instanceValue as NetworkModule
            }
    }

    private fun httpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    private fun retrofitInstance(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getApiService(retrofit: Retrofit): PharmacyApi {
        return retrofit.create(PharmacyApi::class.java)
    }
}