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
import androidx.navigation.compose.rememberNavController
import com.example.rentchecktfg2026.presentation.ui.utils.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoringScreen() {

    var salario by remember { mutableStateOf("") }

    var alquiler by remember { mutableStateOf("") }

    var contrato by remember { mutableStateOf("Indefinido") }

    var antiguedad by remember { mutableStateOf("") }

    var ingresosExtra by remember { mutableStateOf(false) }

    var impagosPrevios by remember { mutableStateOf(false) }

    var score by remember { mutableStateOf<Int?>(null) }


    val azul = Color(0xFF2D63ED)
    val gris = Color(0xFFF7F9FC)


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cálculo de solvencia estimada", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = azul,
                    titleContentColor = Color.White
                )
                )
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

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Información adicional",
                        style = MaterialTheme.typography.labelLarge,
                        color = azul
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = ingresosExtra, onCheckedChange = { impagosPrevios = it })
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
                    score = calcularScoring(
                        salario.toIntOrNull() ?: 0,
                        alquiler.toIntOrNull() ?: 0,
                        contrato,
                        antiguedad.toIntOrNull() ?: 0,
                        ingresosExtra,
                        impagosPrevios
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = azul),
                shape = RoundedCornerShape(12.dp)

            ) {
                Icon(Icons.Default.Analytics, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("CALCULAR SCORING ESTIMADO", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(32.dp))

            score?.let {
                val color = colorSemaforo(it)


                Text(

                    "Resultado: $it / 100"

                )


                Spacer(modifier = Modifier.height(20.dp))


                Box(

                    modifier = Modifier

                        .size(140.dp)

                        .background(color)

                )

            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun ScoringScreenPreview(){

    ScoringScreen()

}