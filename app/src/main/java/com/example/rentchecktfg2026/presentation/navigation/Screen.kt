package com.example.rentchecktfg2026.presentation.navigation


sealed class Screen(val route:String){

    object Registro: Screen("registro")

    object Login: Screen("login")

    object PerfilInquilino: Screen("perfil_inquilino")

    object Scoring: Screen("scoring")

}