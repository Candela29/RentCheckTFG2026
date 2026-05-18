package com.example.rentchecktfg2026.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.data.repositories.DocumentRepositoryImpl
import com.example.rentchecktfg2026.data.repositories.UserRepositoryImpl
import com.example.rentchecktfg2026.domain.repositories.DocumentRepository
import com.example.rentchecktfg2026.domain.repositories.UserRepository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class InquilinoPerfilViewModel(
    private val userRepo: UserRepository,
    private val docRepo: DocumentRepository
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()


    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()


    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _telefono= MutableStateFlow("")
    val telefono: StateFlow<String> =  _telefono.asStateFlow()


    private val _dniSubido = MutableStateFlow(false)
    val dniSubido: StateFlow<Boolean> = _dniSubido.asStateFlow()


    private val _nominaSubida = MutableStateFlow(false)
    val nominaSubida: StateFlow<Boolean> = _nominaSubida.asStateFlow()


    init{
        cargarDatosUsuario()
    }
    fun subidaDocumento(uri: Uri, esDni: Boolean) {
        val uid = auth.currentUser?.uid ?: return
        Log.d("SUBIDA", "UID: $uid")
        Log.d("SUBIDA", "URI: $uri")
        val tipo = if (esDni) "DNI" else "NOMINA"

        viewModelScope.launch(Dispatchers.IO) {

            Log.d("SUBIDA", "Llamando al servidor...")
            // Usamos docRepo
            val result = docRepo.uploadDocument(uri, if(esDni) "DNI" else "NOMINA", uid)

            Log.d("SUBIDA", "Resultado: ${result.isSuccess}")
            Log.d("SUBIDA", "Error: ${result.exceptionOrNull()?.message}")

            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    if (esDni) _dniSubido.value = true else _nominaSubida.value = true
                } else {
                    // Mostrar error
                }
            }
        }
    }


    fun cargarDatosUsuario() {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val result = userRepo.getUserById(uid)
            result.getOrNull()?.let { user ->
                _nombre.value = user.name
                _email.value = user.email
                _telefono.value = user.telefono
            }
        }
    }
}



