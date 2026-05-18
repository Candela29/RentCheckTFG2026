package com.example.rentchecktfg2026.presentation.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.setValue

import androidx.navigation.NavController
import com.example.rentchecktfg2026.domain.model.Property
import com.example.rentchecktfg2026.presentation.navigation.Screen
import com.example.rentchecktfg2026.presentation.ui.components.MenuDeAcciones
import com.example.rentchecktfg2026.presentation.viewmodels.PropiedadViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListaPropiedades (propiedadViewModel: PropiedadViewModel= koinViewModel(),
                      navController: NavController){

    val propiedades by propiedadViewModel.listaPropiedades.collectAsState()
    val azul = Color(0xFF2D63ED)

    //cargamos los datos
    LaunchedEffect(Unit) {
        propiedadViewModel.cargarMisPropiedades()
    }

    Scaffold(

        topBar = {
            MenuDeAcciones(navController=navController, titulo = "Lista de Inmuebles", rol= "INMOBILIARIA")

        },

        floatingActionButton = {

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                FloatingActionButton(
                    onClick = { navController.popBackStack() },
                    containerColor = azul,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Atrás"
                    )
                }


                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AltaPropiedad.route) },
                    containerColor = azul
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir Propiedad",
                        tint = Color.White
                    )
                }
            }
        },


    ) {

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
                        PropertyCard(property = propiedad, propiedadViewModel = propiedadViewModel, navController = navController)

                    }

                }
            }
        }
    }



}
@Composable
fun PropertyCard(property: Property,propiedadViewModel: PropiedadViewModel, navController: NavController){
    var showDialog by remember {mutableStateOf(false)}
    val azul = Color(0xFF2D63ED)
    var expanded by remember {mutableStateOf(false)}
    val azulFondoCard = Color(0xFFE8EFFF)
    if(showDialog){
        AlertDialog(
            onDismissRequest = {showDialog=false},
            title={Text(text="Eliminar propiedad")},
            text = {Text("¿Estás seguro de eliminar la propiedad?")},
            confirmButton = {
                Button(onClick =
                    {propiedadViewModel.removePropiedad(property.id.toString())
                        showDialog = false
                    },
                    colors= ButtonDefaults.buttonColors(
                        containerColor = azul,
                        contentColor = Color.White
                    )
                    ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = {showDialog=false},
                    colors= ButtonDefaults.buttonColors(
                        containerColor = azul,
                        contentColor = Color.White
                    )) {
                    Text("Cancelar")
                }
            }
        )
    }
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 4.dp),

        elevation= CardDefaults.cardElevation(3.dp),
        shape = RoundedCornerShape(16.dp),
        colors= CardDefaults.cardColors(containerColor = azulFondoCard ),
        onClick = {expanded= !expanded}
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = azul
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = property.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (!expanded) {
                        Row {
                            Text(
                                text = "${property.price} €",
                                style = MaterialTheme.typography.bodyMedium,
                                color = azul,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            // AÑADIMOS LOS METROS AQUÍ
                            Text(
                                text = "${property.size} m²",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }


            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {


                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, null, modifier = Modifier.size(20.dp), tint = azul)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "Tipo: ${property.propertyType?.ifEmpty { "No especificado" } ?: "No especificado"}", style = MaterialTheme.typography.bodyMedium)
                    }


                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AttachMoney, null, modifier = Modifier.size(20.dp), tint = azul)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "Precio total: ${property.price} €", style = MaterialTheme.typography.bodyMedium)
                    }


                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Home, null, modifier = Modifier.size(20.dp), tint = azul)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = "Superficie: ${property.size ?: 0} m²", style = MaterialTheme.typography.bodyMedium)
                    }


                    if (property.hasElevator == true) {
                        ExtraItem(azul, "Tiene ascensor")
                    }
                    if (property.hasPool == true) {
                        ExtraItem(Color(0xFF4CAF50), "Piscina comunitaria")
                    }
                    if (property.hasAirConditioning == true) {
                        ExtraItem(Color(0xFF4CAF50), "Aire acondicionado")
                    }
                    if (property.hasHeating == true) {
                        ExtraItem(Color(0xFF4CAF50), "Calefacción central")
                    }
                }

            }
                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = {
                    propiedadViewModel.seleccionarPropiedad(property)

                        navController.navigate(Screen.AltaPropiedad.route)
                     }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar", tint = azul)
                    }

                    Spacer(modifier = Modifier.width(8.dp))


                    IconButton(
                        onClick = { showDialog = true }, // Solo abre el diálogo
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = azul
                        )
                    }
                }
            }
        }
    }
@Composable
fun ExtraItem(color: Color, texto: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Check, null, modifier = Modifier.size(20.dp), tint = color)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = texto, style = MaterialTheme.typography.bodyMedium)
    }
}