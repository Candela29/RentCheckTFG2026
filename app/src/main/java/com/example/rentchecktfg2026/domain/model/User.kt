package com.example.rentchecktfg2026.domain.model
import com.google.firebase.firestore.DocumentId

data class User (
    @DocumentId val id:String ="",
    val name:String ="",
    val email:String ="",
    val role:String="", //"INQUILINO o INMOBILIARIA"
    val telefono:String="",
    val description:String="",

    // Estos campos son opcionales
    val dniUrl: String = "",
    val nominaUrl: String = "",
    val contractType: String = "",
    val scoring: Int = 0,
    val cif: String? = null // Por ejemplo, esto solo lo tendría la Inmobiliaria
)