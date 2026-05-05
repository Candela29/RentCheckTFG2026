package com.example.rentchecktfg2026.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistroViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val userRepository= UserRepository(FirebaseFirestore.getInstance())

    // 1. Definimos los estados (Estilo StateFlow)
    private val _loading = MutableStateFlow(false)
    val loading= _loading.asStateFlow()

    private val _mensaje = MutableStateFlow("")
    val mensaje = _mensaje.asStateFlow()

    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso= _registroExitoso.asStateFlow()

    fun registrarUsuario(nombre: String, email: String, telefono: String, password: String, rol: String) {
        // CORRECCIÓN: Usamos .value para asignar
        _mensaje.value = ""

        if (nombre.isBlank() || email.isBlank() || telefono.isBlank() || password.isBlank() || rol.isBlank()) {
            _mensaje.value = "Por favor, completa todos los campos"
            return
        }

        _loading.value = true

        Log.d("REGISTRO", "Intentando crear usuario: $email")

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                Log.d("REGISTRO", "✅ Usuario creado en Auth: ${result.user?.uid}")
                val uid = result.user?.uid ?: ""

                val nuevoUsuario = User(
                    id = uid,
                    name = nombre,
                    email = email,
                    telefono= telefono,
                    role = rol.uppercase()
                )

                viewModelScope.launch {
                    Log.d("REGISTRO", "Guardando en Firestore...")
                    val guardadoOk = userRepository.saveUser(nuevoUsuario)
                    Log.d("REGISTRO", "Firestore resultado: $guardadoOk")
                    _loading.value = false
                    if (guardadoOk) {
                        Log.d("REGISTRO", "✅ Poniendo registroExitoso = true")
                        _registroExitoso.value = true
                    } else {
                        _mensaje.value = "Error al guardar los datos en el perfil"
                    }
                }
            }
            .addOnFailureListener {
                Log.e("REGISTRO", "❌ Error en Auth: ${it.message}")
                _loading.value = false
                _mensaje.value = "Error en registro: ${it.message}"
            }
    }
    fun resetEstado(){
        _registroExitoso.value=false
    }
}