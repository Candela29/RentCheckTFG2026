package com.example.rentchecktfg2026.domain.model
import com.google.firebase.firestore.DocumentId

data class User (
    @DocumentId val id:String ="",
    val name:String ="",
    val email:String ="",
    val role:String="",
    val telefono:String="",

    // Estos campos son opcionales
    val dniUrl: String? = null,
    val nominaUrl: String? = null,
    val scoring: Int? = null,
    val CIF: String? = null // Por ejemplo, esto solo lo tendría la Inmobiliaria
){
    constructor() : this("","","")
}