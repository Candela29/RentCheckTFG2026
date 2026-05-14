package com.example.rentchecktfg2026.domain.model

data class Property(
    val id: Long? = null,
    val title: String = "",
    val address: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val description: String = "",
    val owner: User? = null,
    val rooms: Int = 0,
    val hasElevator: Boolean = false,
    val isFurnished: Boolean = false,
    val hasGarage: Boolean = false,
    val propertyType: String = "",
    val size: Int=0,
    val hasPool: Boolean=false,
    val hasAirConditioning: Boolean=false,
    val hasHeating: Boolean=false,
)