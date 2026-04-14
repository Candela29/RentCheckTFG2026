package com.example.rentchecktfg2026.presentation.ui.utils

import androidx.compose.ui.graphics.Color

fun calcularScoring(

    salario:Int,
    alquiler:Int,
    contrato:String,
    antiguedad:Int,
    ingresosExtra:Boolean,
    impagosPrevios:Boolean

):Int{

    var puntos = 0


    // 1 esfuerzo financiero

    val ratio = alquiler * 100 / salario

    puntos += when{

        ratio <= 30 -> 40

        ratio <= 35 -> 30

        ratio <= 40 -> 20

        else -> 0

    }


    // 2 estabilidad laboral

    puntos += when(contrato){

        "Indefinido" -> 25

        "Funcionario" -> 25

        "Temporal" -> 15

        "Autonomo" -> 10

        else -> 5

    }


    // 3 antiguedad

    puntos += when{

        antiguedad >= 5 -> 15

        antiguedad >= 2 -> 10

        antiguedad >= 1 -> 5

        else -> 0

    }


    // 4 ingresos extra

    if(ingresosExtra)

        puntos += 10




    if(!impagosPrevios)

        puntos += 10


    return puntos.coerceIn(0,100)

}



fun colorSemaforo(score:Int):Color{

    return when{

        score >= 80 -> Color(0xFF4CAF50)

        score >= 60 -> Color(0xFFFFC107)

        else -> Color(0xFFF44336)

    }

}