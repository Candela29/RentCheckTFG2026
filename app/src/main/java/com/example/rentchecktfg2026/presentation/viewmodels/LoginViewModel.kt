package com.example.rentchecktfg2026.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.data.repositories.UserRepositoryImpl
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.example.rentchecktfg2026.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
): ViewModel(

) {

    private val auth = FirebaseAuth.getInstance()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()


    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible = _passwordVisible.asStateFlow()

    // Nuevo: Estado para saber qué rol tiene el que loguea
    private val _roleResult = MutableStateFlow<String?>(null)
    val roleResult = _roleResult.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()


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
        val userEmail = _username.value
        val pass = _password.value
        if (userEmail.isBlank() || pass.isBlank()) {
            _error.value = "Rellena todos los campos"
            return
        }

        _error.value = ""

        auth.signInWithEmailAndPassword(_username.value, _password.value)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid
                if (uid != null) {
                    viewModelScope.launch {

                        val res = repository.getUserById(uid)
                        val user = res.getOrNull()

                        if (user != null) {
                            try {
                                RetrofitClient.instance.syncUser(user)
                                Log.d("LOGIN", "Usuario sincronizado con backend")
                            } catch (e: Exception) {
                                Log.e("LOGIN", "Error sync: ${e.message}")
                            }
                            _roleResult.value = user.role
                        }
                        }
                    }
                }
            }

    }
