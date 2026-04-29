package com.example.rentchecktfg2026.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
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

    private fun isValidPassword(password: String): Boolean {

        if (password.contains(" ")) return false

        //caracteres obligatorios
        val regex = Regex(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$!%*?&._-]).{12,64}$"
        )

        return regex.matches(password)
    }

    fun registrarUsuario(nombre: String, email: String, telefono: String, password: String, confirmPassword: String, rol: String) {
        // CORRECCIÓN: Usamos .value para asignar
        _mensaje.value = ""

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
            _mensaje.value =
                "La contraseña debe tener 12+ caracteres, mayúscula, minúscula, número y símbolo"
            return
        }

        if (rol.uppercase() !in listOf("INQUILINO", "INMOBILIARIA")) {
            _mensaje.value = "Rol no permitido"
            return
        }

        _loading.value = true

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: ""

                val nuevoUsuario = User(
                    id = uid,
                    name = nombre,
                    email = email,
                    telefono= telefono,
                    role = rol.uppercase(),
                    emailVerified = false,
                    documentExpiryAt = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    val guardadoOk = userRepository.saveUser(nuevoUsuario)
                    _loading.value = false
                    if (guardadoOk) {
                        _registroExitoso.value = true
                    } else {
                        _mensaje.value = "Error al guardar los datos en el perfil"
                    }
                }
            }
            .addOnFailureListener {
                _loading.value = false
                _mensaje.value = "Error en registro: ${it.message}"
            }

    }


    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}