package com.example.rentchecktfg2026.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentchecktfg2026.presentation.viewmodels.LoginViewModel

import com.example.rentchecktfg2026.R
import com.example.rentchecktfg2026.presentation.navigation.Screen
import com.example.rentchecktfg2026.ui.theme.RentCheckTFG2026Theme

@Composable
fun Login(
    navController: NavController,
    loginViewModel: LoginViewModel= viewModel()
){
    val username by loginViewModel.username.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val passwordVisible by loginViewModel.passwordVisible.collectAsState()
    val roleResult by loginViewModel.roleResult.collectAsState()
    val error by loginViewModel.error.collectAsState()

    val azul= Color(0xFF2D63ED)
    val background = Color.White

    LaunchedEffect(roleResult) {
        roleResult?.let{
            role->
            when(role.uppercase()){
                "INQUILINO" -> {
                    navController.navigate(Screen.PerfilInquilino.route)
                }
                "INMOBILIARIA" -> {
                    navController.navigate(Screen.MenuInmobiliaria.route)
                } }
        }
    }

    Scaffold (containerColor = background){
            innerPadding->
        Column (modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){

            Image(
                painter= painterResource(id=R.drawable.logo),
                contentDescription = "Logo Rent Check",
                modifier = Modifier
                    .size(150.dp).padding(16.dp),
                contentScale = ContentScale.Fit
            )
            Text(text="Bienvenido de nuevo", color=Color.Black,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold)

            Spacer(modifier= Modifier.height(16.dp))

            OutlinedTextField(
                value=username,
                onValueChange = {loginViewModel.setUsername(it)},
                label={Text("Correo electrónico")},
                leadingIcon = {
                    Icon(Icons.Default.Email,contentDescription=null, tint = azul)},
                modifier = Modifier.fillMaxWidth(),
                shape= RoundedCornerShape(8.dp)

            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value=password,
                onValueChange = {loginViewModel.setPassword(it)},
                label={Text("Contraseña")},
                leadingIcon = {Icon(imageVector = Icons.Default.Lock, contentDescription = null,tint = azul)},
                visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier=Modifier.fillMaxWidth(),
                trailingIcon = {
                    val image= if(passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = {loginViewModel.setPasswordVisible()}) {
                        Icon(imageVector = image, contentDescription = null,tint = azul)
                    }
                },
                shape=RoundedCornerShape(8.dp)
            )
            Spacer(modifier=Modifier.height(24.dp))

            Button(
                onClick = {
                    loginViewModel.onLoginClick()
                },
                modifier = Modifier.fillMaxWidth()
                    .height(56.dp),
                shape= RoundedCornerShape(8.dp),
                colors = buttonColors(
                    containerColor = azul
                )

            ){
                Text("INICIAR SESIÓN")
            }

            if (error.isNotEmpty()) {
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Row (
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "¿No tienes cuenta?",
                    color = Color.Gray
                )
                TextButton(
                    onClick = {navController.navigate(Screen.Registro.route)}
                ) {
                    Text(
                        text = "Regístrate",
                        color=azul,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLogin(){
    //Login(rememberNavController())

        // Pasa un NavController vacío que no haga nada
        Login(navController = rememberNavController())

}