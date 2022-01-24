package com.example.pharmacyappd.model

data class PharmsResponse(
    val message: List<String>,
    val result: List<Pharm>,
    val status: Boolean
)