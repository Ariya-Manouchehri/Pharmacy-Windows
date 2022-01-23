package com.example.pharmacyappd.model

data class Order(
    val created_at: String,
    val date: String,
    val delivered: Boolean,
    val doctor: String,
    val id: Int,
    val paid: Boolean,
    val patient_id: Int,
    val total_price: Int,
    val updated_at: String
)