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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class InquilinoPerfilViewModel(
    private val userRepo: UserRepository,
    private val docRepo: DocumentRepository
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Estado para el Nombre
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    // Estado para el Email
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _telefono= MutableStateFlow("")
    val telefono: StateFlow<String> =  _telefono.asStateFlow()

    // Estado del DNI (False = no subido)
    private val _dniSubido = MutableStateFlow(false)
    val dniSubido: StateFlow<Boolean> = _dniSubido.asStateFlow()

    // Estado de la Nómina (False = no subida)
    private val _nominaSubida = MutableStateFlow(false)
    val nominaSubida: StateFlow<Boolean> = _nominaSubida.asStateFlow()


    init{
        cargarDatosUsuario()
    }
    fun subidaDocumento(uri: Uri, esDni: Boolean) {
        val uid = auth.currentUser?.uid ?: return
        val tipo = if (esDni) "DNI" else "NOMINA"

        viewModelScope.launch {
            // Usamos docRepo
            val result = docRepo.uploadDocument(uri, tipo, uid)

            if (result.isSuccess) {
                if (esDni) _dniSubido.value = true else _nominaSubida.value = true
            } else {
                Log.e("SUBIDA", "Error: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    // Funciones por si quieres rellenar los datos desde otra pantalla
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



