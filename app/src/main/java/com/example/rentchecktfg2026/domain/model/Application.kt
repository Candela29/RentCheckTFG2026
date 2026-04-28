package com.example.rentchecktfg2026.domain.model

data class Application(
    val id: Long? = null,
    val property: Property? = null,
    val tenant: User? = null,
    val status: String = "Pendiente"
)
