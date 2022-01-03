package com.example.pharmacyappd.model

data class LoginResponse(
    val message: List<String> = listOf(),
    val result: List<UserAndToken> = listOf(),
    val status: Boolean,
)