package com.example.pharmacyappd.model

data class MedsAllResponse(
    val message: List<String>,
    val result: List<MedInfo>,
    val status: Boolean
)