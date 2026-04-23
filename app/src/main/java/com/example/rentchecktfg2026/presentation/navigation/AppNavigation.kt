package com.example.rentchecktfg2026.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rentchecktfg2026.presentation.ui.screens.AltaPropiedad
import com.example.rentchecktfg2026.presentation.ui.screens.InquilinoPerfil
import com.example.rentchecktfg2026.presentation.ui.screens.Login
import com.example.rentchecktfg2026.presentation.ui.screens.RegistroScreen
import com.example.rentchecktfg2026.presentation.ui.screens.ScoringScreen


@Composable
fun AppNavigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route

    ){

        composable(Screen.Registro.route){

            RegistroScreen(navController)
        }

        composable(Screen.Scoring.route){
            ScoringScreen()
        }

        composable (Screen.AltaPropiedad.route){
            AltaPropiedad()
        }
        composable (Screen.Login.route) {
            Login(navController)
        }

        composable (Screen.PerfilInquilino.route ){
            InquilinoPerfil(navController)
        }


    }

}