package com.example.rentchecktfg2026.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    // Estado para el usuario
    private val auth = FirebaseAuth.getInstance()
    private val userRepository= UserRepository(FirebaseFirestore.getInstance())
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    // Estado para la contraseña
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    // Estado para la visibilidad de la contraseña
    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible = _passwordVisible.asStateFlow()

    // Nuevo: Estado para saber qué rol tiene el que loguea
    private val _roleResult = MutableStateFlow<String?>(null)
    val roleResult = _roleResult.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    // Funciones para actualizar los estados
    fun setUsername(newValue: String) {
        _username.value = newValue
    }

    fun setPassword(newValue: String) {
        _password.value = newValue
    }

    fun setPasswordVisible() {
        _passwordVisible.value = !_passwordVisible.value
    }

    fun onLoginClick() {
        // Aquí iría tu lógica de Firebase más adelante
        val user = _username.value
        val pass = _password.value
       if(user.isBlank()|| pass.isBlank()){
           _error.value="Rellena todos los campos"
           return
       }

        _error.value = ""

        auth.signInWithEmailAndPassword(_username.value, _password.value)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid
                if (uid != null) {
                    viewModelScope.launch {
                        val user = userRepository.getUserById(uid)

                        if (user != null) {
                            // Aquí llega el "INQUILINO" o "INMOBILIARIA" de tu Firestore
                            _roleResult.value = user.role
                        } else {
                            _error.value = "No se encontraron datos de perfil"
                        }
                    }
                }
            }
            .addOnFailureListener {
                _error.value = "Error: ${it.message}"
            }
    }
}