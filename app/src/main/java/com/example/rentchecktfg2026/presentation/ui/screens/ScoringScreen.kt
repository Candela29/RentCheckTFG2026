package com.example.rentchecktfg2026.presentation.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
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
import kotlin.contracts.contract
import kotlin.math.exp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoringScreen(navController: NavController,
    scoringViewModel: ScoringViewModel = koinViewModel(),) {

    var salario by remember { mutableStateOf("") }

    var alquiler by remember { mutableStateOf("") }



    var antiguedad by remember { mutableStateOf("") }

    var ingresosExtra by remember { mutableStateOf(false) }

    var impagosPrevios by remember { mutableStateOf(false) }

    var score by remember { mutableStateOf<Int?>(null) }
    var contrato by remember { mutableStateOf("Indefinido") }
    var expanded by remember { mutableStateOf(false) }
    val opcionesContrato = listOf("Indefinido", "Temporal", "Autónomo", "Funcionario","Fijo")

    val azul = Color(0xFF2D63ED)
    val gris = Color(0xFFF7F9FC)


    Scaffold(
        topBar = {
            MenuDeAcciones(navController=navController,titulo="Cálculo de scoring",rol="INQUILINO")

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
                        label = { Text("Salario mensual neto") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = alquiler,
                        onValueChange = { alquiler = it },
                        label = { Text("Precio del alquiler") },
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

                    Spacer(modifier = Modifier.height(20.dp))

                   Box(modifier = Modifier.fillMaxWidth()) {
                       OutlinedTextField(
                           value = contrato,
                           onValueChange = {},
                           label = {Text("Tipo de contrato")},
                           modifier = Modifier.fillMaxWidth(),
                           shape = RoundedCornerShape(12.dp),
                           trailingIcon = {
                               IconButton(onClick = { expanded=true }) {
                                   Icon(
                                       imageVector = if(expanded) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                                       contentDescription = null
                                   )
                               }
                           }
                       )
                       //Hago la caja clickable
                       Box(
                           modifier = Modifier
                               .matchParentSize()
                               .padding(top = 8.dp) // Para no tapar el label
                               .clickable { expanded = true }
                       )
                       DropdownMenu(
                           expanded=expanded,
                           onDismissRequest = {expanded=false},
                           modifier = Modifier.fillMaxWidth(0.8f)
                       ) {
                           opcionesContrato.forEach { opcion->
                               DropdownMenuItem(
                                   text={Text(opcion)},
                                   onClick = {
                                       contrato=opcion
                                       expanded=false //cierro menú
                                   }
                               )
                           }
                       }
                   }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Información adicional",
                        style = MaterialTheme.typography.labelLarge,
                        color = azul
                    )

                    Row(
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

                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    // 1. Convertimos los textos a números de forma segura
                    val s = salario.toIntOrNull() ?: 0
                    val a = alquiler.toIntOrNull() ?: 0
                    val ant = antiguedad.toIntOrNull() ?: 0

                    // 2. IMPORTANTE: calcularScoring ahora devuelve un OBJETO (ScoringResult), no un Int
                    val resultadoCompleto = calcularScoring(
                        salario = s,
                        alquiler = a,
                        contrato = contrato,
                        antiguedad = ant,
                        ingresosExtra = ingresosExtra,
                        impagosPrevios = impagosPrevios
                    )

                    // 3. Usamos la función del ViewModel que acepta estos nuevos tipos
                    // Nota: Asegúrate de que el ViewModel tenga esta función llamada calcularYGuardar
                    scoringViewModel.calcularYGuardar(
                        s = s.toDouble(),
                        a = a.toDouble(),
                        contrato = contrato,
                        ant = ant,
                        result = resultadoCompleto
                    )

                    // 4. Si quieres guardar el score local para mostrarlo abajo:

                    score = resultadoCompleto.total
                    navController.navigate(Screen.DetalleScoring.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = azul),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Analytics, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("ANALIZAR SOLVENCIA", fontWeight = FontWeight.Bold)
            }
        }



    }
}

@Preview(showBackground = true)
@Composable
fun ScoringScreenPreview(){

    ScoringScreen(rememberNavController())

}