package com.example.rentchecktfg2026.domain.model
import com.google.firebase.firestore.DocumentId

class Inquilino(
    @DocumentId val id: String ="",
    val name:String ="",
    val email:String="",
    val description:String="",
    val dniUrl:String="",
    val nominaUrl:String ="",
    val scoring:Int=0,
    val role:String= "INQUILINO"
) {

    constructor() : this("", "", "", "", "", "", 0, "INQUILINO")
}