package com.example.pharmacyappd.model

data class OrdersResponse(
    val message: List<String>,
    val result: List<OrderAndPatient>,
    val status: Boolean
)