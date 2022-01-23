package com.example.pharmacyappd.model

data class MedsAllInfoResponse(
    val message: List<String>,
    val result: MedAllInfo,
    val status: Boolean
)