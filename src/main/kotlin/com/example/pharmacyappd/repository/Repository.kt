package com.example.pharmacyappd.repository

import com.example.pharmacyappd.model.Company
import kotlinx.coroutines.runBlocking

class Repository private constructor() {

    private val pharmacyApi = NetworkModule.instance.pharmacyApi

    private var accessToken = ""

    companion object {

        private var instanceValue: Repository? = null
        val instance: Repository
            get() {
                if (instanceValue == null) {
                    instanceValue = Repository()
                }
                return instanceValue as Repository
            }
    }

    fun setAccessToken(token: String) {
        accessToken = "Bearer $token"
    }

    fun removeAccessToken() {
        accessToken = ""
    }

    fun login(phone: String, password: String) = runBlocking {
        pharmacyApi.login(phone, password)
    }

    fun findUser(phone: String, nationalNumber: String) = runBlocking {
        pharmacyApi.getUserInfoByPhone(phone, nationalNumber)
    }

    fun resetPassword(id: Int, password: String) = runBlocking {
        pharmacyApi.resetPassword(id, password)
    }

    fun getAllMeds() = runBlocking {
        pharmacyApi.getMeds(accessToken)
    }

    fun getOrders() = runBlocking {
        pharmacyApi.getOrders(accessToken)
    }

    fun getCategories() = runBlocking {
        pharmacyApi.getCategories(accessToken)
    }

    fun getPharms() = runBlocking {
        pharmacyApi.getPharms(accessToken)
    }
}