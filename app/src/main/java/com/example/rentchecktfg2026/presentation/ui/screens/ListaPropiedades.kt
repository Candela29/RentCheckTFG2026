package com.example.rentchecktfg2026.presentation.ui.screens

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rentchecktfg2026.domain.model.Property
import com.example.rentchecktfg2026.presentation.viewmodels.PropiedadViewModel

@Composable
fun ListaPropiedades (propiedadViewModel: PropiedadViewModel= viewModel(),
                      navController: NavController){

    val propiedades by propiedadViewModel.listaPropiedades.collectAsState()

    //cargamos los datos
    LaunchedEffect(Unit) {
        propiedadViewModel.cargarMisPropiedades()
    }

    Scaffold {
        innerPadding->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text("Mis anuncios",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(propiedades){ propiedad->
                    key(propiedad.id) {
                        PropertyCard(property = propiedad)

                    }

                }
            }
        }
    }



}
@Composable
fun PropertyCard(property: Property){
    val azul = Color(0xFF2D63ED)
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation= CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier= Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(property.title, fontWeight = FontWeight.Bold,style= MaterialTheme.typography.titleMedium)
                Text(text = "${property.price} €", color = azul, fontWeight = FontWeight.Bold )
                Text(text = "Tipo: ${property.propertyType}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}