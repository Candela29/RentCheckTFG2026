package com.example.rentchecktfg2026.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.example.rentchecktfg2026.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistroViewModel(private val repository: UserRepository) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _mensaje = MutableStateFlow("")
    val mensaje = _mensaje.asStateFlow()

    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso = _registroExitoso.asStateFlow()


    private val _politicasAceptadas = MutableStateFlow(false)
    val politicasAceptadas = _politicasAceptadas.asStateFlow()

    fun actualizarPoliticas(aceptado: Boolean) {
        _politicasAceptadas.value = aceptado
    }

    private fun isValidPassword(password: String): Boolean {
        if (password.contains(" ")) return false
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$!%*?&._-]).{12,64}$")
        return regex.matches(password)
    }

    fun registrarUsuario(nombre: String, email: String, telefono: String, password: String, confirmPassword: String, rol: String) {
        _mensaje.value = ""

        //  Checkbox politicas de privacidad
        if (!_politicasAceptadas.value) {
            _mensaje.value = "Debes aceptar las políticas de privacidad"
            return
        }

        if (nombre.isBlank() || email.isBlank() || telefono.isBlank() || password.isBlank() || rol.isBlank()) {
            _mensaje.value = "Por favor, completa todos los campos"
            return
        }

        if (!isValidEmail(email)) {
            _mensaje.value = "Email inválido"
            return
        }

        if (password != confirmPassword) {
            _mensaje.value = "Las contraseñas no coinciden"
            return
        }

        if (!isValidPassword(password)) {
            _mensaje.value = "Contraseña débil (12+ caracteres, Mayus, Minus, Num, Símbolo)"
            return
        }

        _loading.value = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                result.user?.sendEmailVerification()
                val uid = result.user?.uid ?: ""

                val nuevoUsuario = User(
                    id = uid,
                    name = nombre,
                    email = email,
                    telefono = telefono,
                    role = rol.uppercase(),
                    emailVerified = false,
                    documentExpiryAt = System.currentTimeMillis(), // Marca de tiempo de aceptación
                    scoring = 0,
                    contractType = "",
                )

                viewModelScope.launch {
                    val guardadoOk = repository.saveUser(nuevoUsuario).getOrDefault(false)
                    if (guardadoOk) {
                        try {
                            RetrofitClient.instance.syncUser(nuevoUsuario)
                            _registroExitoso.value = true
                        } catch (e: Exception) {
                            Log.e("REGISTRO", "Error sync backend: ${e.message}")
                            _registroExitoso.value = true
                        }
                    } else {
                        _loading.value = false
                        _mensaje.value = "Error al guardar perfil"
                    }
                }
            }
            .addOnFailureListener {
                _loading.value = false
                _mensaje.value = "Error en registro: ${it.message}"
            }
    }

    private fun isValidEmail(email: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun resetEstado() {
        _registroExitoso.value = false
    }
}