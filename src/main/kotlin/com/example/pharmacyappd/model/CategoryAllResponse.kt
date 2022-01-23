package com.example.pharmacyappd.model

data class CategoryAllResponse(
    val message: List<String>,
    val result: List<CategoryAndImage>,
    val status: Boolean
)