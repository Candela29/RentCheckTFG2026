package com.example.rentchecktfg2026.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.rentchecktfg2026.navigation.Screen
import com.example.rentchecktfg2026.R
import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.model.User
import com.example.rentchecktfg2026.presentation.navigation.Screen

@Composable
fun RegistroScreen(navController: NavController) {

    var rol by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var mensaje by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }

    val prefijos = listOf(
        "+34 🇪🇸",
        "+504 🇭🇳",
        "+52 🇲🇽",
        "+54 🇦🇷",
        "+1 🇺🇸"
    )

    var prefijoSeleccionado by remember {
        mutableStateOf(prefijos[0])
    }

    val isPreview = LocalInspectionMode.current

    val auth = if (!isPreview) FirebaseAuth.getInstance() else null
    val db = if (!isPreview) FirebaseFirestore.getInstance() else null


    Scaffold { innerPadding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ){

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.rentcheck),
                contentDescription = "logo",
                modifier = Modifier.size(210.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Regístrate para comenzar",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("1. Elige perfil")

            Spacer(modifier = Modifier.height(10.dp))

            Row{

                CardRol("Inquilino", rol == "Inquilino") {
                    rol = "Inquilino"
                }

                Spacer(modifier = Modifier.width(10.dp))

                CardRol("Inmobiliaria", rol == "Inmobiliaria") {
                    rol = "Inmobiliaria"
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                leadingIcon = {
                    Icon(Icons.Default.Person, null)
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(Icons.Default.Email, null)
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))




            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){


                Box{

                    Row(
                        modifier = Modifier
                            .border(
                                1.dp,
                                Color.Gray,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                expanded = true
                            }
                            .padding(
                                horizontal = 12.dp,
                                vertical = 16.dp
                            )
                    ){

                        Icon(
                            Icons.Default.Phone,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(prefijoSeleccionado)

                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ){

                        prefijos.forEach{

                            DropdownMenuItem(
                                text = {
                                    Text(it)
                                },
                                onClick = {

                                    prefijoSeleccionado = it
                                    expanded = false

                                }
                            )

                        }

                    }

                }

                Spacer(modifier = Modifier.width(10.dp))




                TextField(

                    value = telefono,

                    onValueChange = {

                        telefono = it.filter {

                                char -> char.isDigit()

                        }

                    },

                    label = { Text("Teléfono") },

                    singleLine = true,

                    modifier = Modifier.weight(1f)

                )

            }

            Spacer(modifier = Modifier.height(10.dp))


            TextField(

                value = password,

                onValueChange = { password = it },

                label = { Text("Password") },

                visualTransformation = PasswordVisualTransformation(),

                leadingIcon = {

                    Icon(Icons.Default.Lock, null)

                },

                singleLine = true,

                modifier = Modifier.fillMaxWidth()

            )

            Spacer(modifier = Modifier.height(20.dp))


            Button(

                colors = ButtonDefaults.buttonColors(

                    containerColor = Color(0xFF2E5A88)

                ),

                enabled = !loading,

                modifier = Modifier.fillMaxWidth(),

                onClick = {


                    if (

                        nombre.isBlank()

                        || email.isBlank()

                        || telefono.isBlank()

                        || password.isBlank()

                        || rol.isBlank()

                    ){

                        mensaje = "Completa todos los campos"
                        return@Button

                    }


                    loading = true


                    val prefijoLimpio =
                        prefijoSeleccionado.split(" ")[0]


                    val telefonoCompleto =
                        prefijoLimpio + telefono


                    if (auth != null && db != null){

                        auth.createUserWithEmailAndPassword(
                            email,
                            password
                        )

                            .addOnSuccessListener {


                                val uid =
                                    auth.currentUser?.uid


                                if(uid != null){

                                    val user = User(

                                        uid = uid,
                                        nombre = nombre,
                                        email = email,
                                        telefono = telefonoCompleto,
                                        rol = rol

                                    )


                                    db.collection("users")

                                        .document(uid)

                                        .set(user)

                                        .addOnSuccessListener {

                                            loading = false


                                            if (rol == "Inquilino"){

                                                navController.navigate(
                                                    Screen.PerfilInquilino.route
                                                )

                                            }
                                            else{

                                                navController.navigate(
                                                    Screen.Login.route
                                                )

                                            }

                                        }

                                }

                            }

                            .addOnFailureListener {

                                loading = false
                                mensaje = it.message ?: "Error"

                            }

                    }

                }

            ){

                if (loading){

                    CircularProgressIndicator(
                        color = Color.White
                    )

                }
                else{

                    Text("Crear cuenta")

                }

            }


            Spacer(modifier = Modifier.height(10.dp))


            Text(
                mensaje,
                color = Color.Red
            )

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