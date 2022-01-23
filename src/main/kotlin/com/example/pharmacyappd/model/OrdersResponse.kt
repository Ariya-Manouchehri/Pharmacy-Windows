package com.example.pharmacyappd.model

data class OrdersResponse(
    val message: List<Any>,
    val result: List<OrderAndPatient>,
    val status: Boolean
)