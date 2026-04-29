package com.example.rentchecktfg2026.presentation.ui.screens


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.tooling.preview.Preview
import com.example.rentchecktfg2026.presentation.navigation.Screen
import com.example.rentchecktfg2026.presentation.viewmodels.InquilinoPerfilViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InquilinoPerfil(
    navController: NavController,
    inquilinoPerfilViewModel: InquilinoPerfilViewModel = viewModel ()
){
    val nombre by inquilinoPerfilViewModel.nombre.collectAsState()
    val email by inquilinoPerfilViewModel.email.collectAsState()
    val dniSubido by inquilinoPerfilViewModel.dniSubido.collectAsState()
    val nominaSubida by inquilinoPerfilViewModel.nominaSubida.collectAsState()

    //Selectores de archivos
    val launcherDni= rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri->
        uri?.let { inquilinoPerfilViewModel.subidaDocumento(it, esDni = true) }
    }

    val launcherNomina = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { inquilinoPerfilViewModel.subidaDocumento(it, esDni = false) }
    }
    val azul= Color(0xFF2D63ED)
    val celeste = Color(0xFFE3EDFF)

    Scaffold (
        topBar = {
            TopAppBar(
                title={Text("Mi perfil de Inquilino", fontWeight = FontWeight.Bold ,color=Color.White)},
                colors= TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = azul
                )
            )
        }
    ){ innerPadding->
        Column(modifier = Modifier.padding(innerPadding)
            .fillMaxSize()
            .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(100.dp),
                colors = CardDefaults.cardColors(containerColor = celeste),
                modifier = Modifier.size(120.dp) // Icono mucho más grande
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(10.dp),
                    tint = azul
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = if(nombre.isEmpty()) "Nombre del Inquilino" else nombre,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold)

            Text(text = if(email.isEmpty()) "usuario@ejemplo.com" else email,
                color = Color.Gray)

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Detalles de la cuenta",
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = azul
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                shape = RoundedCornerShape(12.dp)
            ){
                Column(modifier=Modifier.padding(16.dp)) {
                    Row(modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text("Ubicación:", color = Color.Gray)
                        Text("Madrid, España", fontWeight = FontWeight.Medium)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Teléfono:", color = Color.Gray)
                        Text("666666666", fontWeight = FontWeight.Medium)
                    }
                }
            }

            Text(
                text = "Documentación necesaria",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- LLAMADA A LAS TARJETAS ---
            DocumentCard(
                titulo = "Subir DNI (PDF)",
                subido = dniSubido,
                onClick = { launcherDni.launch("application/pdf") }
            )

            DocumentCard(
                titulo = "Subir Nómina (PDF)",
                subido = nominaSubida,
                onClick = { launcherNomina.launch("application/pdf") }
            )
            Spacer(modifier = Modifier.height(24.dp))

            //Resumen
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Estado del perfil", fontWeight = FontWeight.Bold)
                    Text(
                        text = if(dniSubido && nominaSubida) "Listo para revisión" else "Pendiente de documentos",
                        color = if(dniSubido && nominaSubida) Color(0xFF4CAF50) else Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Button(
                onClick = {navController.navigate(Screen.Scoring.route)},
                enabled = dniSubido && nominaSubida,
                modifier= Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape= RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = azul)
            )
            {Text("CALCULAR SCORING", fontWeight = FontWeight.Bold) }

        }
    }
}
@Composable
fun DocumentCard(titulo:String, subido:Boolean, onClick:()->Unit ) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Icono dinámico: cambia según el estado 'subido'
            Icon(
                imageVector = if (subido) Icons.Default.CheckCircle else Icons.Default.AddCircle,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = if (subido) Color(0xFF4CAF50) else Color(0xFF2D63ED)
            )

            // Usamos Spacer como en tus apuntes para separar el icono del texto
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = if (subido) "Archivo cargado correctamente" else "Haz clic para seleccionar PDF",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

    }
}
@Preview
@Composable
fun previewInquilino() {
    InquilinoPerfil(viewModel())
}
