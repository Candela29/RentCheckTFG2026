package com.example.rentchecktfg2026.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rentchecktfg2026.presentation.navigation.Screen
import com.example.rentchecktfg2026.presentation.ui.components.MenuDeAcciones
import com.example.rentchecktfg2026.presentation.ui.utils.colorSemaforo
import com.example.rentchecktfg2026.presentation.viewmodels.ScoringViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetalleScoring(navController: NavController,scoringViewModel: ScoringViewModel = koinViewModel ()){
    val res = scoringViewModel.resultadoScoring
    val snackbarHostState=remember{ SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val azul= Color(0xFF2D63ED)
    val colorPrincipal = if(res!=null) colorSemaforo(res.total) else Color.Gray

    Scaffold(
        snackbarHost = {SnackbarHost(snackbarHostState)},

        topBar = {
            MenuDeAcciones(navController=navController,titulo="Cálculo estimado del scoring",rol="INQUILINO")

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.popBackStack()
                },
                containerColor = azul,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Atrás"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (res == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay datos disponibles. Por favor, calcula el scoring.")
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ){

                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            "PUNTUACIÓN FINAL",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.Gray
                        )
                        Text(
                            text = "${res.total}",
                            fontSize = 72.sp,
                            fontWeight = FontWeight.Black,
                            color = colorPrincipal
                        )

                        Text(
                            text = when {
                                res.total >= 80 -> "CANDIDATO EXCELENTE"
                                res.total >= 60 -> "RIESGO MODERADO"
                                else -> "RIESGO ELEVADO"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = colorPrincipal
                        )
                    }
                }

                Spacer(modifier= Modifier.height(24.dp))

                Text(
                    text = "Desglose del Análisis",
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                //Ratio de pago (máx 40 puntos)
                ItemBarra("Capacidad Financiera",res.puntosFinanciero,40,Color(0xFF4CAF50))

                //Estabilidad contrato(máximo 25 puntos)
                ItemBarra("Estabilidad Laboral", res.puntosContrato,25,Color(0xFF2196F3))

                //Antigüedad (máximo 15 puntos)
                ItemBarra("Garantías y pago", res.puntosExtras, 20, Color(0xFF9C27B0))

                Spacer(modifier = Modifier.weight(1f))


                Button(
                    onClick = {

                        val scoring = res?.total ?: 0
                        val tipoContrato = res?.contrato ?: ""
                        scoringViewModel.enviarExpediente(scoring, tipoContrato)
                        scope.launch {
                            snackbarHostState.showSnackbar("Expediente enviado correctamente")
                        }

                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors= ButtonDefaults.buttonColors(containerColor = azul),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Send,contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ENVIAR EXPEDIENTE A LA INMOBILIARIA")
                }
            }
        }
    }
}


@Composable
fun ItemBarra (titulo: String, puntos: Int, maximo: Int, colorBarra: Color){
    val progreso = puntos.toFloat() / maximo.toFloat()
    Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(titulo, style = MaterialTheme.typography.bodyMedium)
            Text("$puntos / $maximo pts", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp)),
            color = colorBarra,
            trackColor = colorBarra.copy(alpha = 0.1f)
        )
    }

}