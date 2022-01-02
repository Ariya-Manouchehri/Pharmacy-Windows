package com.example.pharmacyappd.model

data class CreatePatientResponse(
    val message: List<String>,
    val result: List<PatientResult>,
    val status: Boolean,
)