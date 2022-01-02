package com.example.pharmacyappd.repository

import kotlinx.coroutines.runBlocking

class Repository private constructor() {

    private val pharmacyApi = NetworkModule.instance.pharmacyApi

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

    fun login(phone: String, password: String) = runBlocking {
        pharmacyApi.login(phone, password)
    }
}