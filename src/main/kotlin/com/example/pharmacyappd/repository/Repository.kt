package com.example.pharmacyappd.repository

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

    fun addNewMed(
        pharm_id: Int,
        comp_id: Int,
        exp_date: String,
        price: Int,
        add_info: String,
        inv: Int
    ) = runBlocking {
        println("entered api")
        val a = pharmacyApi.newMed(accessToken, pharm_id, comp_id, exp_date, price, add_info, inv)
        println("exited api")
        return@runBlocking a
    }

    fun getOrders() = runBlocking {
        pharmacyApi.getOrders(accessToken)
    }

    fun getOrderContent(id: Int) = runBlocking {
        pharmacyApi.getOrderContent(accessToken, id)
    }

    fun getCategories() = runBlocking {
        pharmacyApi.getCategories(accessToken)
    }

    fun getPharms() = runBlocking {
        pharmacyApi.getPharms(accessToken)
    }

    fun getCompanies() = runBlocking {
        pharmacyApi.getCompanies(accessToken)
    }

    fun getProfile() = runBlocking {
        pharmacyApi.getProfile(accessToken)
    }
}