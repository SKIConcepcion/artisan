package com.example.artisan.domain.model

data class User(
    val email: String = "",
    val password: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val notifications: List<String> = emptyList(),
    val projects: List<String> = emptyList()
)
