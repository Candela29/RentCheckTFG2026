package com.example.rentchecktfg2026.presentation.navigation


sealed class Screen(val route:String){

    object Registro: Screen("registro")

    object Login: Screen("login")

    object PerfilInquilino: Screen("perfil_inquilino")

    object Scoring: Screen("scoring")

    object AltaPropiedad: Screen("Alta_Propiedad")
    object CardRol: Screen("CardRol")

    object CandidatosAct: Screen("Candidatos")
    object MenuInmobiliaria:Screen ("Menú_inmobiliaria")

    object ListaInmubeles :Screen(" Lista_Inmuebles")



}