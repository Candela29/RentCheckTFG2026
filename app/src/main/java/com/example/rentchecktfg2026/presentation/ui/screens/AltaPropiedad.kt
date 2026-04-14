package com.example.rentchecktfg2026.presentation.ui.screens


import android.R
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.rentchecktfg2026.presentation.viewmodels.PropiedadViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AltaPropiedad(
    // navController: NavController,
    propiedadViewModel: PropiedadViewModel = viewModel()
) {
    val titulo by propiedadViewModel.titulo.collectAsState()
    val precio by propiedadViewModel.precio.collectAsState()
    val direccion by propiedadViewModel.direccion.collectAsState()
    val habitaciones by propiedadViewModel.habitaciones.collectAsState()
    val tieneAscensor by propiedadViewModel.tieneAscensor.collectAsState()
    val estaAmueblado by propiedadViewModel.estaAmueblado.collectAsState()
    val azul = Color(0xFF2D63ED)
    val celeste = Color(0xFFE3EDFF)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Propiedad", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = azul)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp) ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- CABECERA ARREGLADA ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp), // Espaciado arriba y abajo
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Vivienda",
                    tint = azul,
                    modifier = Modifier.size(56.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Los textos ahora están al lado del icono
                Column {
                    Text(
                        text = "Detalles de la Vivienda",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Completa los datos para publicar",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            // --- INFORMACIÓN BÁSICA ---
            Text(
                text = "Información General",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color=Color.DarkGray,
                modifier = Modifier.padding( 8.dp)
            )
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { propiedadViewModel.setTitulo(it) },
                        label = { Text("Nombre del inmueble") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null,
                                tint = azul
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
                        OutlinedTextField(
                            value = precio,
                            onValueChange = {nuevoTexto->
                                propiedadViewModel.setPrecio(nuevoTexto)
                            },
                            label = { Text("Precio (€)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f).padding(end = 8.dp),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = habitaciones,
                            onValueChange = {
                                    nuevoTexto->
                                propiedadViewModel.setHabitaciones(nuevoTexto)
                            },
                            label = { Text("Hab.") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(0.6f),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- CARACTERÍSTICAS (CHECKBOXES) ---
            Text("Características",
                fontWeight = FontWeight.Bold,
                style=MaterialTheme.typography.titleMedium,
                color=Color.DarkGray)

            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    // Item Ascensor
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().clickable { propiedadViewModel.toggleAscensor(!tieneAscensor) }
                    ) {
                        Checkbox(
                            checked = tieneAscensor,
                            onCheckedChange = { propiedadViewModel.toggleAscensor(it) },
                            colors = CheckboxDefaults.colors(checkedColor = azul)
                        )
                        Text(text = "Tiene ascensor")
                    }

                    // Item Amueblado
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().clickable { propiedadViewModel.toggleAmueblado(!estaAmueblado) }
                    ) {
                        Checkbox(
                            checked = estaAmueblado,
                            onCheckedChange = { propiedadViewModel.toggleAmueblado(it) },
                            colors = CheckboxDefaults.colors(checkedColor = azul)
                        )
                        Text(text = "Está amueblado")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().clickable { propiedadViewModel.toggleAmueblado(!estaAmueblado) }
                    ) {
                        Checkbox(
                            checked = estaAmueblado,
                            onCheckedChange = { propiedadViewModel.toggleAmueblado(it) },
                            colors = CheckboxDefaults.colors(checkedColor = azul)
                        )
                        Text(text = "Tiene garaje")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Esto empuja el botón abajo

            // BOTÓN PUBLICAR
            Button(
                onClick = { propiedadViewModel.registrarPropiedad() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = azul)
            ) {
                Text("PUBLICAR PROPIEDAD", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
@Preview
@Composable
fun previewAlta(){
    AltaPropiedad()
}