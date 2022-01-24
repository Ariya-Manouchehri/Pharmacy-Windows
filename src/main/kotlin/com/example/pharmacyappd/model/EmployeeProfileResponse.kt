package com.example.pharmacyappd.model

data class EmployeeProfileResponse(
    val message: List<Any>,
    val result: List<EmployeeProfile>,
    val status: Boolean
)