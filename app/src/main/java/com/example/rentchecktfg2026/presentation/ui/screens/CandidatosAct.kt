package com.example.rentchecktfg2026.presentation.ui.screens

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.presentation.viewmodels.CandidatosViewModel
import com.example.rentchecktfg2026.ui.theme.RentCheckTFG2026Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidatosAct(
    navController: NavController,
    vm: CandidatosViewModel) {
    val candidatos by vm.candidatos.observeAsState(emptyList())
    val azul= Color(0xFF2D63ED)
    val gris = Color(0xFFF7F9FC)


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Candidatos Rent-Check", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = azul,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = gris
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    "Candidatos registrados",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { vm.filterTop() },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = azul),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Filtrar Solvencia Alta")
                }
            }

                if(candidatos.isEmpty()){
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.People,contentDescription = null)
                            Text("No hay candidatos aún", color = Color.Gray)
                        }
                    }
                }else{
                    LazyColumn {
                        items(candidatos) { candidato ->
                            CandidatoCard(candidato)
                        }
                    }
                }
            }


            }

    }


@Composable
fun CandidatoCard(c: User) {
    val colorPuntos=when {
        c.scoring>70-> Color(0xFF4CAF50)
        c.scoring> 40 -> Color(0xFFFFC107)
        else -> Color(0xFFF44336)
    }
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
                Text(text = c.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = c.email, color = Color.Gray, fontSize = 14.sp)
                Text(text = "Contrato: ${c.contractType}", fontSize = 12.sp)
            }

            // El Semáforo de puntuación
            val colorPuntos = when {
                c.scoring > 70 -> Color(0xFF4CAF50) // Verde
                c.scoring > 40 -> Color(0xFFFFC107) // Amarillo
                else -> Color(0xFFF44336) // Rojo
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${c.scoring}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = colorPuntos
                )
                Text("puntos", fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}

/*@Composable
@Preview(showBackground = true)

fun CandidatosActPreview() {

    CandidatosAct(rememberNavController())

}*/