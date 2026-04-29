package com.example.rentchecktfg2026.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import com.example.rentchecktfg2026.R
import com.example.rentchecktfg2026.domain.model.User

import com.example.rentchecktfg2026.presentation.navigation.Screen
import com.example.rentchecktfg2026.presentation.viewmodels.RegistroViewModel

@Composable
fun RegistroScreen(
    navController: NavController,
    registroViewModel: RegistroViewModel = viewModel()
) {
    // Estados locales para los formularios
    var rol by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    // Estados observados del ViewModel
    val loading by registroViewModel.loading.collectAsState()
    val mensaje by registroViewModel.mensaje.collectAsState()
    val registroExitoso by registroViewModel.registroExitoso.collectAsState()

    val prefijos = listOf("+34 🇪🇸", "+504 🇭🇳", "+52 🇲🇽", "+54 🇦🇷", "+1 🇺🇸")
    var prefijoSeleccionado by remember { mutableStateOf(prefijos[0]) }

    // Navegación automática cuando el registro es exitoso
    LaunchedEffect(registroExitoso) {
        if (registroExitoso) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Registro.route) { inclusive = true }
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) //habilita scroll
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .heightIn(max = 220.dp) //limite de altura
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text("Regístrate para comenzar", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(20.dp))

            Text("1. Elige perfil")
            Spacer(modifier = Modifier.height(10.dp))


            Row {
                CardRol("Inquilino", rol == "Inquilino") { rol = "Inquilino" }
                Spacer(modifier = Modifier.width(10.dp))
                CardRol("Inmobiliaria", rol == "Inmobiliaria") { rol = "Inmobiliaria" }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Campos de texto comunes
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Teléfono con prefijo
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box {
                    Row(
                        modifier = Modifier
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .clickable { expanded = true }
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                    ) {
                        Icon(Icons.Default.Phone, null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(prefijoSeleccionado)
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        prefijos.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = { prefijoSeleccionado = item; expanded = false }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                TextField(
                    value = telefono,
                    onValueChange = { input -> telefono = input.filter { it.isDigit() } },
                    label = { Text("Teléfono") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Password") },
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            // BOTÓN SIMPLIFICADO: Ahora solo llama al ViewModel
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E5A88)),
                enabled = !loading,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val prefijoLimpio = prefijoSeleccionado.split(" ")[0]
                    registroViewModel.registrarUsuario(
                        nombre = nombre,
                        email = email,
                        telefono = prefijoLimpio + telefono,
                        password = password,
                        confirmPassword = confirmPassword,
                        rol = rol
                    )
                }
            ) {
                if (loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Crear cuenta")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Mensajes de error del ViewModel
            if (mensaje.isNotEmpty()) {
                Text(text = mensaje, color = Color.Red)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview(){

    RegistroScreen(
        navController = rememberNavController()
    )

}