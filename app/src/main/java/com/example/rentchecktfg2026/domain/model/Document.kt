package com.example.rentchecktfg2026.domain.model

data class Document(
    val id: Long? = null,
    val documentType: String = "",
    val fileUrl: String = "",
    val status: String = "Pendiente",
    val comment: String? = null,
    val user: User? = null
)
