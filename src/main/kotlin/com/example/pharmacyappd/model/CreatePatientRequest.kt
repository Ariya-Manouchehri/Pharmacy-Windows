package com.example.pharmacyappd.model

data class CreatePatientRequest(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val nationalNumber: String,
    val insuranceNumber: String?,
    val insuranceId: Int?,
)