package com.example.pharmacyapp.model

import com.example.pharmacyappd.model.UserAndToken

data class LoginResponse(
    val message: List<String> = listOf(),
    val result: List<UserAndToken> = listOf(),
    val status: Boolean,
)