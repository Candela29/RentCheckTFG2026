package com.example.rentchecktfg2026.presentation.ui.screens

import android.util.Log
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.presentation.ui.components.MenuDeAcciones
import com.example.rentchecktfg2026.presentation.viewmodels.CandidatosViewModel
import com.example.rentchecktfg2026.ui.theme.RentCheckTFG2026Theme
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidatosAct(
    navController: NavController,
    vm: CandidatosViewModel= koinViewModel()
 ) {
    val candidatos by vm.candidatos.observeAsState(emptyList())
    val azul= Color(0xFF2D63ED)
    val gris = Color(0xFFF7F9FC)


    Scaffold(
        topBar = {
            MenuDeAcciones(navController=navController, titulo = "Plantilla de Candidatos", rol= "INMOBILIARIA")


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
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { vm.filterTop() },
                    colors = ButtonDefaults.buttonColors(containerColor = azul),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Filtrar Solvencia Alta", fontSize = 12.sp)
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
                            CandidatoCard(candidato, vm)
                        }
                    }
                }
            }


            }

    }


@Composable
fun CandidatoCard(c: User,candidatosViewModel: CandidatosViewModel) {
var showDialog by remember { mutableStateOf(false) }
val azul = Color(0xFF2D63ED)
    if(showDialog){
        AlertDialog(
            onDismissRequest = {showDialog=false},
            title = {Text("Eliminar Candidato")},
            text = {Text("¿Estás seguro de que quieres eliminar a ${c.name} de la lista")},
            confirmButton = {
                Button(onClick =
                    {candidatosViewModel.ocultarCandidatoDeLaLista(c.id)
                    showDialog=false},
                    colors = ButtonDefaults.buttonColors(containerColor = azul)
                ){
                    Text("Eliminar", color=Color.White)
                }
            },
            dismissButton = {
                Button(onClick = {showDialog=false},
                    colors = ButtonDefaults.buttonColors(containerColor = azul)) { Text("Cancelar")}
            }
        )
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
            IconButton(onClick = { candidatosViewModel.toggleFavorito(c) }) {
                Icon(
                    imageVector = if (c.favorito) Icons.Filled.Star else Icons.Default.StarBorder,
                    contentDescription = "Favorito",
                    tint = if (c.favorito) Color(0xFFFFD700) else Color.Gray
                )
            }
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
            IconButton(onClick =
                { Log.d("CLICK", "Pulsado borrar de ${c.name}")
                    showDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar Candidato",
                    tint = azul
                )
            }
        }
    }
}

/*@Composable
@Preview(showBackground = true)

fun CandidatosActPreview() {

    CandidatosAct(rememberNavController())

}*/