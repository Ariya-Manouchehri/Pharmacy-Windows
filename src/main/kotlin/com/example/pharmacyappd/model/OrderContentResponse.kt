package com.example.pharmacyappd.model

data class OrderContentResponse(
    val message: List<String>,
    val result: List<OrderContent>,
    val status: Boolean
)