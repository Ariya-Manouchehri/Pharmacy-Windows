package com.example.pharmacyappd.model

data class CompaniesResponse(
    val message: List<String>,
    val result: List<Company>,
    val status: Boolean
)