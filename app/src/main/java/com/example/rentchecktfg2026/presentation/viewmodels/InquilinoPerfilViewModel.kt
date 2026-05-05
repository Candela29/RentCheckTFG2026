package com.example.rentchecktfg2026.presentation.viewmodels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class InquilinoPerfilViewModel(
    private val repository: UserRepository= UserRepository()

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
        // Aquí en el futuro conectarás con Firebase Storage
        val uid= FirebaseAuth.getInstance().currentUser?.uid ?:return
        val tipo =if(esDni)"dni" else "nomina"
        val campoFirestore= if (esDni) "dniUrl" else "nominaUrl"
        viewModelScope.launch {
            //Subir a Storage
            val urlDescarga = repository.subirDocumento(uri,tipo)

            if (urlDescarga != null) {

                // PASO B: Guardamos esa URL en la ficha de Firestore del usuario
                // ¡Aquí es donde usas el otro método!
                val exito = repository.updateDocumentUrl(
                    id = uid,
                    campo = campoFirestore,
                    url = urlDescarga
                )
                if(exito){
                    if(esDni) _dniSubido.value= true else _nominaSubida.value= true
                }
            }
        }

    }

    // Funciones por si quieres rellenar los datos desde otra pantalla
    fun cargarDatosUsuario() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            // Quitamos el viewModelScope.launch porque Firebase ya es asíncrono
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Importante: comprueba que los nombres coincidan con los de Firestore
                        _nombre.value = document.getString("name") ?: "Sin nombre"
                        _email.value= document.getString("email") ?: ""
                        _telefono.value = document.getString("telefono") ?: ""
                    } else {
                        _nombre.value = "Usuario no encontrado"
                    }
                }
                .addOnFailureListener { exception ->
                    _nombre.value = "Error al cargar"
                    println("Error Firebase: ${exception.message}")
                }
        } else {
            _nombre.value = "No hay sesión activa"
        }
    }
}



