package com.example.pharmacyappd.model

data class MedsAllResponse(
    val message: List<String>,
    val result: List<MedAllInfo>,
    val status: Boolean
)