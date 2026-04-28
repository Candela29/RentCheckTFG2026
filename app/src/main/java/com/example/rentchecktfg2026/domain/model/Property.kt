package com.example.rentchecktfg2026.domain.model

data class Property(
    val id: Long? = null,
    val title: String = "",
    val address: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val description: String = "",
    val owner: User? = null
)