package com.example.pharmacyappd.model

data class Content(
    val created_at: String,
    val id: Int,
    val ins_buy: Boolean,
    val med_id: Int,
    val presc_id: Int,
    val price: Int,
    val updated_at: String
)