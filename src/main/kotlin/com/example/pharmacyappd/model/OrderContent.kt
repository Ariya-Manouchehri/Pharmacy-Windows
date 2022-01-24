package com.example.pharmacyappd.model

data class OrderContent(
    val content: Content,
    val image: String,
    val med: Med,
    val pharm: List<Pharm>
)