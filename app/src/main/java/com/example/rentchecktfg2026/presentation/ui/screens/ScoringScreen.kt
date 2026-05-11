package com.example.rentchecktfg2026.presentation.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentchecktfg2026.presentation.navigation.Screen
import com.example.rentchecktfg2026.presentation.ui.components.MenuDeAcciones
import com.example.rentchecktfg2026.presentation.ui.utils.*
import com.example.rentchecktfg2026.presentation.viewmodels.ScoringViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoringScreen(scoringViewModel: ScoringViewModel = koinViewModel(), navController: NavController) {

    var salario by remember { mutableStateOf("") }

    var alquiler by remember { mutableStateOf("") }

    var contrato by remember { mutableStateOf("") }

    var antiguedad by remember { mutableStateOf("") }

    var ingresosExtra by remember { mutableStateOf(false) }

    var impagosPrevios by remember { mutableStateOf(false) }

    var score by remember { mutableStateOf<Int?>(null) }


    val azul = Color(0xFF2D63ED)
    val gris = Color(0xFFF7F9FC)


    Scaffold(
        topBar = {
            MenuDeAcciones(navController=navController, titulo = "Cálculo de Scoring", rol= "INQUILINO")
        },
        containerColor = gris
    ) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )

        {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {


                Column(modifier = Modifier.padding(16.dp)) {

                    OutlinedTextField(
                        value = salario,
                        onValueChange = { salario = it },
                        label = { Text("Salario mensual") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = alquiler,
                        onValueChange = { alquiler = it },
                        label = { Text("Precio del alquiler ") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = antiguedad,
                        onValueChange = { antiguedad = it },
                        label = { Text("Años de antigüedad") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Información adicional",
                        style = MaterialTheme.typography.labelLarge,
                        color = azul
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = ingresosExtra, onCheckedChange = { ingresosExtra = it })
                        Text("Cuento con ingresos extra comprobables")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = impagosPrevios,
                            onCheckedChange = { impagosPrevios = it })
                        Text(
                            "No tengo impagos en los últimos 5 años,",
                            color = if (impagosPrevios) Color.Black else Color.Gray
                        )
                    }
                    Button(
                        modifier=Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = azul,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        onClick = {
                            //Convertimos a números (usamos 0 si está vacío)
                            val s = salario.toIntOrNull() ?: 0
                            val a = alquiler.toIntOrNull() ?: 0
                            val ant = antiguedad.toIntOrNull() ?: 0

                            if (s > 0) {
                                // 2. Calculamos el ScoringResult (el paquete con el desglose)
                                val resultadoCompleto = calcularScoring(
                                    salario = s,
                                    alquiler = a,
                                    contrato = contrato,
                                    antiguedad = ant,
                                    ingresosExtra = ingresosExtra,
                                    impagosPrevios = impagosPrevios
                                )

                                // 3. Enviamos al ViewModel para guardar y subir a la nube
                                scoringViewModel.calcularYGuardar(
                                    s = s.toDouble(),
                                    a = a.toDouble(),
                                    contrato = contrato,
                                    ant = ant,
                                    result = resultadoCompleto
                                )

                                // 4. Navegamos a la pantalla de detalle que acabamos de crear
                                navController.navigate(Screen.DetalleScoring.route)
                            }
                        }
                    ){
                        Text("ANALIZAR SOLVENCIA", fontWeight = FontWeight.Bold)
                    }

                }
            }

            Spacer(modifier = Modifier.height(24.dp))

        }


    }
}
/*
@Preview(showBackground = true)
@Composable
fun ScoringScreenPreview(){

    ScoringScreen(rememberNavController())

}*/