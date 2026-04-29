package com.example.rentchecktfg2026.presentation.ui.screens


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardRol(

    titulo:String,

    seleccionado:Boolean,

    onClick:()->Unit

){

    Box(

        modifier = Modifier

            .fillMaxWidth()

            .height(80.dp)

            .border(

                2.dp,

                if(seleccionado)

                    Color.Blue

                else

                    Color.Gray,

                RoundedCornerShape(10.dp)

            )

            .clickable{

                onClick()

            },

        contentAlignment = Alignment.Center

    ){

        Text(titulo)

    }

}