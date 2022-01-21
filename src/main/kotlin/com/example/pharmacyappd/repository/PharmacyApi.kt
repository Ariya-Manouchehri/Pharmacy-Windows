package com.example.pharmacyappd.repository

import com.example.pharmacyappd.model.LoginResponse
import com.example.pharmacyappd.model.MedsAllResponse
import com.example.pharmacyappd.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PharmacyApi {

    @POST("api/login/employee")
    suspend fun login(
        @Query("phone") phone: String,
        @Query("password") password: String,
    ): Response<LoginResponse>

    @POST("api/user/findEmployee")
    suspend fun getUserInfoByPhone(
        @Query("phone") phone: String,
        @Query("nat_num") nationalNumber: String
    ): Response<UserResponse>

    @POST("api/user/reset")
    suspend fun resetPassword(
        @Query("id") id: Int,
        @Query("password") password: String,
    ): Response<UserResponse>

    @GET("api/meds")
    suspend fun getMeds(
        @Header("Authorization") token: String
    ): Response<MedsAllResponse>
}