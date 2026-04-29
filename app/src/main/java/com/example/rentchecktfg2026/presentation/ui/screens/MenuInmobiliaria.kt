package com.example.rentchecktfg2026.presentation.ui.screens

import android.view.Menu
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rentchecktfg2026.presentation.navigation.Screen

@Composable
fun MenuInmobiliaria(){
    val azul =Color(0xFF2D63ED)

    Scaffold() {
        innerPadding->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),

        ) {
            Text(
                text = "Panel de Gestión",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botón para subir propiedad
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(80.dp),
                shape = RoundedCornerShape(12.dp),
                colors = buttonColors(
                    containerColor = azul
                )

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("SUBIR PROPIEDAD", fontWeight = FontWeight.Bold)
                }


        }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón para ver candidatos
            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth().height(80.dp),
                shape = RoundedCornerShape(12.dp),
                colors = buttonColors(
                    containerColor = azul
                )

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("VER CANDIDATOS", fontWeight = FontWeight.Bold)
                }
                 }
        }
    }
    }

@Composable
@Preview
fun PreviewMenuInmobiliaria(){
    MenuInmobiliaria()
}