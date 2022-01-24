package com.example.pharmacyappd.repository

import com.example.pharmacyappd.model.*
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

    @POST("api/meds")
    suspend fun newMed(
        @Header("Authorization") token: String,
        @Query("pharm_id") pharm_id: Int,
        @Query("comp_id") comp_id: Int,
        @Query("exp_date") exp_date: String,
        @Query("price") price: Int,
        @Query("add_info") add_info: String,
        @Query("inv") inv: Int,
    )

    @GET("api/orders")
    suspend fun getOrders(
        @Header("Authorization") token: String
    ) : Response<OrdersResponse>

    @GET("api/categories")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ) : Response<CategoryAllResponse>

    @GET("api/pharms")
    suspend fun getPharms(
        @Header("Authorization") token: String
    ) : Response<PharmsResponse>

    @GET("api/comps")
    suspend fun getCompanies(
        @Header("Authorization") token: String
    ) : Response<CompaniesResponse>
}