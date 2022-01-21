package com.example.pharmacyappd.model

data class MedAllInfoResponse(
    val message: List<String>,
    val result: MedAllInfo,
    val status: Boolean
)