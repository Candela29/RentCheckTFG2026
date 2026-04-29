package com.example.rentchecktfg2026.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.rentchecktfg2026.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDeAcciones(navController: NavController){

    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("Menú de acciones") },
        actions={
            IconButton(onClick = {expanded=true}) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Perfil") },
                    onClick = {
                        // Acción 1
                        expanded = false
                        navController.navigate(Screen.PerfilInquilino.route)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Login") },
                    onClick = {
                        // Acción 1
                        expanded = false
                        navController.navigate(Screen.Login.route)
                    }
                )
            }
        }

    )
}