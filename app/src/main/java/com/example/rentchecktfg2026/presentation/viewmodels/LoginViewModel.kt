package com.example.rentchecktfg2026.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel: ViewModel() {
    // Estado para el usuario
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    // Estado para la contraseña
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    // Estado para la visibilidad de la contraseña
    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible.asStateFlow()

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
        println("Intentando login con: $user")
    }
}
