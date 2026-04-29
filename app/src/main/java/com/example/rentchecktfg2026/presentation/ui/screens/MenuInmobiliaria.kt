package com.example.rentchecktfg2026.presentation.ui.screens

import android.view.Menu
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentchecktfg2026.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuInmobiliaria(navController: NavController) {
    val azulOscuro = Color(0xFF1A45A0) // Un azul más profundo para el texto
    val azulFondoCard = Color(0xFFE8EFFF) // Un azul muy suave para el fondo de la card
    val azulIcono = Color(0xFF2D63ED) // Tu azul original para los iconos

    Scaffold(
        topBar = {
            TopAppBar(
                title={
                    Text("Panel de Gestión",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                },

                colors = TopAppBarDefaults.topAppBarColors(containerColor = azulIcono)

            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),

            ) {
            Text(
                text = "Selecciona una acción para continuar",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )


            Spacer(modifier = Modifier.height(40.dp))

            // Card 1: SUBIR PROPIEDAD
            Card(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp), // Aumentamos la altura para que sea más grande
                shape = RoundedCornerShape(24.dp), // Esquinas más redondeadas
                colors = CardDefaults.cardColors(containerColor = azulFondoCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .padding(24.dp)
                            .align(Alignment.CenterStart),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icono más grande y con un fondo circular sutil

                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null,
                                tint = azulIcono,
                                modifier = Modifier.padding(12.dp)
                            )


                        Spacer(modifier = Modifier.width(20.dp))

                        Column {
                            Text(
                                text = "SUBIR PROPIEDAD",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = azulOscuro
                            )
                            Text(
                                text = "Añade un nuevo inmueble",
                                style = MaterialTheme.typography.bodyMedium,
                                color = azulOscuro.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Card 2: VER CANDIDATOS
            Card(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp), // Misma altura grande
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = azulFondoCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .padding(24.dp)
                            .align(Alignment.CenterStart),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = azulIcono,
                                modifier = Modifier.padding(12.dp)
                            )


                        Spacer(modifier = Modifier.width(20.dp))

                        Column {
                            Text(
                                text = "VER CANDIDATOS",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = azulOscuro
                            )
                            Text(
                                text = "Revisa las solicitudes",
                                style = MaterialTheme.typography.bodyMedium,
                                color = azulOscuro.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewMenuInmobiliaria(){
    MenuInmobiliaria(rememberNavController())
}