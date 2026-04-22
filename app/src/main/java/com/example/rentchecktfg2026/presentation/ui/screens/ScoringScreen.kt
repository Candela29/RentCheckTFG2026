package com.example.rentchecktfg2026.presentation.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.rentchecktfg2026.presentation.ui.utils.*


@Composable
fun ScoringScreen(){

    var salario by remember { mutableStateOf("") }

    var alquiler by remember { mutableStateOf("") }

    var contrato by remember { mutableStateOf("Indefinido") }

    var antiguedad by remember { mutableStateOf("") }

    var ingresosExtra by remember { mutableStateOf(false) }

    var impagosPrevios by remember { mutableStateOf(false) }

    var score by remember { mutableStateOf<Int?>(null) }


    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)

    ){

        Text(

            "Calcular solvencia",

            style = MaterialTheme.typography.titleLarge

        )


        Spacer(modifier = Modifier.height(20.dp))


        TextField(

            value = salario,

            onValueChange = { salario = it },

            label = { Text("Salario mensual") }

        )


        TextField(

            value = alquiler,

            onValueChange = { alquiler = it },

            label = { Text("Precio alquiler") }

        )


        TextField(

            value = contrato,

            onValueChange = { contrato = it },

            label = { Text("Tipo contrato") }

        )


        TextField(

            value = antiguedad,

            onValueChange = { antiguedad = it },

            label = { Text("Años antigüedad") }

        )


        Row(verticalAlignment = Alignment.CenterVertically){

            Checkbox(

                checked = ingresosExtra,

                onCheckedChange = {

                    ingresosExtra = it

                }

            )

            Text("Ingresos extra")

        }


        Row(verticalAlignment = Alignment.CenterVertically){

            Checkbox(

                checked = impagosPrevios,

                onCheckedChange = {

                    impagosPrevios = it

                }

            )

            Text("Impagos previos")

        }


        Spacer(modifier = Modifier.height(20.dp))


        Button(

            colors = ButtonDefaults.buttonColors(

                containerColor = Color(0xFF2E5A88)

            ),

            onClick = {

                score = calcularScoring(

                    salario.toIntOrNull() ?: 0,

                    alquiler.toIntOrNull() ?: 0,

                    contrato,

                    antiguedad.toIntOrNull() ?: 0,

                    ingresosExtra,

                    impagosPrevios

                )

            }

        ){

            Text("Calcular scoring")

        }


        Spacer(modifier = Modifier.height(30.dp))


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

@Preview(showBackground = true)
@Composable
fun ScoringScreenPreview(){

    ScoringScreen()

}