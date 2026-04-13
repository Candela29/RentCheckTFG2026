package com.example.tfg.presentation

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentchecktfg2026.ui.theme.RentCheckTFG2026Theme
import com.example.tfg.domain.model.Candidato

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidatosAct(vm: CandidatosViewModel) {
    val candidatos by vm.candidatos.observeAsState(emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Candidatos Rent-Check", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF8F9FA)) // Gris muy clarito de fondo
        ) {
            Button(
                onClick = { vm.filterTop() },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) {
                Text("Filtrar Solvencia Alta")
            }

            LazyColumn {
                items(candidatos) { candidato ->
                    CandidatoCard(candidato)
                }
            }
        }
    }
}

@Composable
fun CandidatoCard(c: Candidato) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = c.nombre, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = c.email, color = Color.Gray, fontSize = 14.sp)
                Text(text = "Contrato: ${c.tipoContrato}", fontSize = 12.sp)
            }

            // El "Semáforo" de puntuación
            val colorPuntos = when {
                c.puntuacion > 70 -> Color(0xFF4CAF50) // Verde
                c.puntuacion > 40 -> Color(0xFFFFC107) // Amarillo
                else -> Color(0xFFF44336) // Rojo
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${c.puntuacion}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = colorPuntos
                )
                Text("puntos", fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CandidatosActPreview() {
    val viewModel = CandidatosViewModel()
    RentCheckTFG2026Theme {
        CandidatosAct(vm = viewModel)
    }
}