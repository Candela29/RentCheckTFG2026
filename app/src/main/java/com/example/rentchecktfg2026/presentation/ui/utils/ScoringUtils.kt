package com.example.rentchecktfg2026.presentation.ui.utils

import androidx.compose.ui.graphics.Color


data class ScoringResult(
    val total: Int,
    val puntosFinanciero: Int,
    val puntosContrato: Int,
    val puntosAntiguedad: Int,
    val puntosExtras: Int,
    val contrato:String=""
)
fun calcularScoring(

    salario:Int,
    alquiler:Int,
    contrato:String,
    antiguedad:Int,
    ingresosExtra:Boolean,
    impagosPrevios:Boolean

): ScoringResult{

    var puntos = 0


    // 1 esfuerzo financiero

    val ratio = alquiler * 100 / salario

    val pFinanciero = when {
        ratio <= 30 -> 40
        ratio <= 35 -> 30
        ratio <= 40 -> 20
        else -> 0
    }


    // 2 estabilidad laboral

    val pContrato = when (contrato) {
        "Indefinido", "Funcionario" -> 25
        "Temporal" -> 15
        "Autonomo" -> 10
        else -> 5
    }


    // 3 antiguedad

    val pAntiguedad = when {
        antiguedad >= 5 -> 15
        antiguedad >= 2 -> 10
        antiguedad >= 1 -> 5
        else -> 0
    }


    // 4 ingresos extra

    var pExtras = 0
    if (ingresosExtra) pExtras += 10
    if (!impagosPrevios) pExtras += 10

    var totalFinal = pFinanciero + pContrato + pAntiguedad + pExtras

// Si el total es mayor a 100, lo dejamos en 100
    if (totalFinal > 100) {
        totalFinal = 100
    }
// Si por algún error fuera menor a 0, lo dejamos en 0
    else if (totalFinal < 0) {
        totalFinal = 0
    }

    return ScoringResult(total=totalFinal, pFinanciero, pContrato, pAntiguedad, pExtras, contrato = contrato)

}



fun colorSemaforo(score:Int):Color{

    return when{

        score >= 80 -> Color(0xFF4CAF50)

        score >= 60 -> Color(0xFFFFC107)

        else -> Color(0xFFF44336)

    }

}