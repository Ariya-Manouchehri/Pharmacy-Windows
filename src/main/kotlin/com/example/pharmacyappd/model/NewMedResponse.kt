package com.example.pharmacyappd.model

data class NewMedResponse(
    val message: List<String>,
    val result: MedResponse,
    val status: Boolean
)