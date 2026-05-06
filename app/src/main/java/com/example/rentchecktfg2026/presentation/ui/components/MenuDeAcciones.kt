package com.example.rentchecktfg2026.presentation.ui.components

import android.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.rentchecktfg2026.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDeAcciones(navController: NavController,titulo:String, rol: String){

    var expanded by remember { mutableStateOf(false) }
    val azul = Color(0xFF2D63ED)

    TopAppBar(
        title = { Text(titulo, fontWeight = FontWeight.Bold, color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = azul),
        actions={
            IconButton(onClick = {expanded=true}) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint=Color.White
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if(rol=="INQUILINO"){
                    DropdownMenuItem(
                        text = {Text("Perfil de Inquilino")},
                        onClick = {
                            expanded=false
                            navController.navigate(Screen.PerfilInquilino.route)
                        }
                    )
                }

                if(rol=="INMOBILIARIA"){
                    DropdownMenuItem(
                        text = {Text("Alta de Propiedad")},
                        onClick = {
                            expanded= false
                            navController.navigate(Screen.MenuInmobiliaria.route)
                        }
                    )
                }
                DropdownMenuItem(
                    text = { Text("Cerrar sesión") },
                    onClick = {

                        expanded = false
                        navController.navigate(Screen.Login.route)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Atrás") },
                    onClick = {

                        expanded = false
                        navController.popBackStack()
                    }
                )
            }
        }

    )
}